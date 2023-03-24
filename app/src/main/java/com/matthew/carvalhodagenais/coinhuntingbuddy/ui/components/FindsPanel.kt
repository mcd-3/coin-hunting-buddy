package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.danger
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

@Composable
fun FindsPanel(
    viewModel: HuntActivityViewModel,
    currentCoinType: Int
) {
    val startPadding = 8.dp
    val endPadding = 8.dp

    val context = LocalContext.current

    // Filter out list of finds by coin type
    val filteredListOfFinds = viewModel.getListOfFindsByCoinType(currentCoinType)

    // FIXME: VERY hacky way to recompose the view!
    val updateCounter = remember { mutableStateOf(0) }

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

            if (updateCounter.value > -1) {
                Text(text = stringResource(id = R.string.finds_label))
            }

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
                        text = stringResource(id = R.string.nothing_found_label),
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
                var filteredIndex = 0
                viewModel.listOfFinds.forEachIndexed { index, it ->
                    if (it.coinTypeId == currentCoinType) {
                        val topPadding = if (filteredIndex == 0) 2.dp else 8.dp

                        filteredIndex++

                        Row(
                            modifier = Modifier.padding(
                                start = startPadding,
                                end = endPadding,
                                top = topPadding
                            )
                        ) {
                            val coinStringArray = FindStringGenerator.generate(
                                context,
                                it.year,
                                it.mintMark,
                                it.variety,
                                it.error
                            )
                            val coinStringFirst = coinStringArray[0]
                            val coinStringSecond = coinStringArray[1]

                            Row() {
                                IconButton(
                                    modifier = Modifier.weight(0.15f),
                                    onClick = {
                                        viewModel.listOfFinds.removeAt(index)
                                        updateCounter.value = updateCounter.value + 1
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = stringResource(id = R.string.cancel_icon_cd),
                                        tint = MaterialTheme.colors.danger
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
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

                                    if (filteredIndex != filteredListOfFinds.size) {
                                        Divider(
                                            color = Color.LightGray,
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(top = 8.dp)
                                        )
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