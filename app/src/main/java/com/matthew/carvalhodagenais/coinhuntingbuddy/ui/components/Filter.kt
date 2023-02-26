package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Filter(
    filterActive: MutableState<Boolean>,
    openFilterDialog: MutableState<Boolean>,
    currentDateFilter: MutableState<DateFilter>,
    currentCoinTypeFilter: MutableState<CoinType?>? = null,
    coroutineScope: CoroutineScope,
    viewModel: MainActivityViewModel
) {
    var selectedDateFilterOption by remember { mutableStateOf(currentDateFilter.value) }
    var selectedCoinTypeFilterOption by remember { mutableStateOf(currentCoinTypeFilter?.value) }

    val allCoinTypes by viewModel.getAllCoinTypes().observeAsState()

    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            if (filterActive.value) {
                Text(
                    text = "Filter active",
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "No filter active",
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        openFilterDialog.value = true
                    }
                }
            ) {
                if (filterActive.value) {
                    Icon(Icons.Filled.FilterAlt, contentDescription = "Filter Active")
                } else {
                    Icon(Icons.Outlined.FilterAlt, contentDescription = "Filter")
                }
            }
        }
    }

    if (openFilterDialog.value) {
        AlertDialog(
            title = { Text(text = "Add/Remove Filter\n") },
            onDismissRequest = {
                openFilterDialog.value = false
                selectedDateFilterOption = currentDateFilter.value
            },
            confirmButton = {
                TextButton(onClick = {
                    openFilterDialog.value = false
                    currentDateFilter.value = selectedDateFilterOption
                    viewModel.dateFilter = currentDateFilter.value

                    if (currentCoinTypeFilter != null) {
                        currentCoinTypeFilter.value = selectedCoinTypeFilterOption
                        viewModel.coinTypeFilter = currentCoinTypeFilter.value
                        filterActive.value =
                            !(currentDateFilter.value == DateFilter.UNSET && currentCoinTypeFilter.value == null)
                    } else {
                        filterActive.value = currentDateFilter.value != DateFilter.UNSET
                    }
                }){
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openFilterDialog.value = false
                    selectedDateFilterOption = currentDateFilter.value
                    selectedCoinTypeFilterOption = currentCoinTypeFilter?.value
                }) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Row {
                    var dateExpanded by remember { mutableStateOf(false) }
                    var coinTypeExpanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = dateExpanded,
                        onExpandedChange = {
                            dateExpanded = !dateExpanded
                        },
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 4.dp)
                            .align(Alignment.Bottom),
                    ) {
                        TextField(
                            readOnly = true,
                            value = TextFieldValue(selectedDateFilterOption.dateFilter),
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = dateExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            label = { Text(text = "Date From") },
                        )
                        ExposedDropdownMenu(
                            expanded = dateExpanded,
                            onDismissRequest = {
                                dateExpanded = false
                            }
                        ) {
                            DateFilter.values().forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        selectedDateFilterOption = it
                                        dateExpanded = false
                                    }
                                ) {
                                    Text(text = it.dateFilter)
                                }
                            }
                        }
                    }

                    if (currentCoinTypeFilter != null) {
                        ExposedDropdownMenuBox(
                            expanded = coinTypeExpanded,
                            onExpandedChange = {
                                coinTypeExpanded = !coinTypeExpanded
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 4.dp)
                                .align(Alignment.Bottom),
                        ) {
                            TextField(
                                readOnly = true,
                                value = TextFieldValue(
                                    if (selectedCoinTypeFilterOption == null) ""
                                    else selectedCoinTypeFilterOption!!.name
                                ),
                                onValueChange = { },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = coinTypeExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                label = { Text(text = "Coin Type") },
                            )
                            ExposedDropdownMenu(
                                expanded = coinTypeExpanded,
                                onDismissRequest = {
                                    coinTypeExpanded = false
                                }
                            ) {
                                allCoinTypes?.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedCoinTypeFilterOption = it
                                            coinTypeExpanded = false
                                        }
                                    ) {
                                        val region = if (it.regionId == 1) "\uD83C\uDDE8\uD83C\uDDE6" else "\uD83C\uDDFA\uD83C\uDDF8"
                                        Text(text = "$region ${it.name}")
                                    }
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}