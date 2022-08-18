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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find

@Composable
fun FindsPanel(
    findsList: MutableList<Find>,
) {
    val startPadding = 8.dp
    val endPadding = 8.dp

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

        if (findsList.isEmpty()) {
            Text(text = "None :(", modifier = Modifier.padding(start = startPadding))
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 8.dp)
            ) {
                findsList.forEachIndexed { index, it ->

                    val topPadding = if (index == 0) 2.dp else 8.dp

                    Row(
                        modifier = Modifier.padding(
                            start = startPadding,
                            end = endPadding,
                            top = topPadding
                        )
                    ) {
                        if (
                            it.year.isEmpty() &&
                            it.mintMark.isEmpty() &&
                            it.variety.isEmpty() &&
                            it.error.isEmpty()
                        ) {
                            Text(text = "Unknown Coin", modifier = Modifier.padding(start = startPadding))
                        } else {
                            val yearStr = it.year.ifEmpty { "Illegible Year" }
                            val mintMarkStr = it.mintMark.ifEmpty { "" }
                            val varietyStr = it.variety.ifEmpty { "" }
                            val errorStr = it.error.ifEmpty { "" }

                            var coinStringFirst = ""
                            var coinStringSecond = ""

                            coinStringFirst = if (it.year.isEmpty() && mintMarkStr.isEmpty()) {
                                yearStr
                            } else if (it.year.isEmpty()) {
                                "$yearStr - $mintMarkStr"
                            } else {
                                "$yearStr$mintMarkStr"
                            }

                            coinStringSecond = if (varietyStr.isEmpty() && errorStr.isEmpty()) {
                                ""
                            } else if (varietyStr.isEmpty()) {
                                errorStr
                            } else if (errorStr.isEmpty()) {
                                varietyStr
                            } else {
                                "$varietyStr - $errorStr"
                            }

                            Row() {
                                IconButton(
                                    modifier = Modifier.weight(0.15f),
                                    onClick = {
                                        findsList.removeAt(index)
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
                                        text = coinStringFirst,
                                        fontSize = 20.sp,
                                    )
                                    if (coinStringSecond.isNotEmpty()) {
                                        Text(
                                            text = coinStringSecond,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }

                                    if (index != findsList.size - 1) {
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