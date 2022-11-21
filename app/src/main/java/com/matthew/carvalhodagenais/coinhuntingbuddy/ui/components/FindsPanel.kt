package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

@Composable
fun FindsPanel(
    viewModel: HuntActivityViewModel,
    currentCoinType: Int
) {
    val startPadding = 8.dp
    val endPadding = 8.dp

    // Filter out list of finds by coin type
    val filteredListOfFinds = viewModel.getListOfFindsByCoinType(currentCoinType)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = startPadding, end = endPadding)
                    .background(Color.Gray)
            )

            Text(text = "Finds")

            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = startPadding, end = endPadding)
                    .background(Color.Gray)
            )
        }

        if (filteredListOfFinds.isEmpty()) {
            Column() {
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "You haven't found anything!",
                        fontSize = 22.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(100.dp),
                        imageVector = Icons.Filled.Search,
                        tint = Color.LightGray,
                        contentDescription = "",
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 8.dp)
            ) {
                viewModel.listOfFinds.forEachIndexed { index, it ->
                    if (it.coinTypeId == currentCoinType) {
                        val topPadding = if (index == 0) 2.dp else 8.dp

                        Row(
                            modifier = Modifier.padding(
                                start = startPadding,
                                end = endPadding,
                                top = topPadding
                            )
                        ) {
                            var coinStringFirst = ""
                            var coinStringSecond = ""

                            if (
                                it.year === null &&
                                it.mintMark.isNullOrEmpty() &&
                                it.variety.isNullOrEmpty() &&
                                it.error.isNullOrEmpty()
                            ) {
                                coinStringFirst = "Unknown Coin"
                            } else {
                                val yearStr = if (it.year === null) "Illegible Year" else it.year.toString()
                                val mintMarkStr = if (it.mintMark.isNullOrEmpty()) "" else it.mintMark
                                val varietyStr = if (it.variety.isNullOrEmpty()) "" else it.variety
                                val errorStr = if (it.error.isNullOrEmpty()) "" else it.error

                                coinStringFirst = if (it.year === null && mintMarkStr!!.isEmpty()) {
                                    yearStr
                                } else if (it.year === null) {
                                    "$yearStr - $mintMarkStr"
                                } else {
                                    "$yearStr$mintMarkStr"
                                }

                                coinStringSecond = if (varietyStr!!.isEmpty() && errorStr!!.isEmpty()) {
                                    ""
                                } else if (varietyStr.isEmpty()) {
                                    errorStr!!
                                } else if (errorStr!!.isEmpty()) {
                                    varietyStr
                                } else {
                                    "$varietyStr - $errorStr"
                                }
                            }

                            Row() {
                                IconButton(
                                    modifier = Modifier.weight(0.15f),
                                    onClick = {
                                        viewModel.listOfFinds.removeAt(index)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = "Cancel",
                                        tint = Color(0xFFE6382C)
                                    )
                                }
                                Column(modifier = Modifier.weight(0.85f)) {
                                    Text(
                                        text = "$coinStringFirst - ${viewModel.getGradeStringById(it.gradeId)}",
                                        fontSize = 20.sp,
                                    )
                                    if (coinStringSecond.isNotEmpty()) {
                                        Text(
                                            text = coinStringSecond,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }

                                    if (index != viewModel.listOfFinds.size - 1) {
                                        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}