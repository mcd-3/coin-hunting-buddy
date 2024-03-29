package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Filter(
    filterActive: MutableState<Boolean>,
    openFilterDialog: MutableState<Boolean>,
    currentDateFilter: MutableState<DateFilter>,
    currentCoinTypeFilter: MutableState<CoinType?>? = null,
    coroutineScope: CoroutineScope,
    isFindsPage: Boolean = false,
    viewModel: MainActivityViewModel
) {
    var selectedDateFilterOption by remember { mutableStateOf(currentDateFilter.value) }
    var selectedCoinTypeFilterOption by remember { mutableStateOf(currentCoinTypeFilter?.value) }

    val allCoinTypes by viewModel.getAllCoinTypes().observeAsState()

    Row(modifier = Modifier.padding(bottom = 6.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            if (filterActive.value) {
                Text(
                    text = stringResource(id = R.string.filter_active_label),
                    modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = stringResource(id = R.string.no_filter_active_label),
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
                    Icon(
                        Icons.Filled.FilterAlt,
                        contentDescription = stringResource(id = R.string.filter_active_cd)
                    )
                } else {
                    Icon(
                        Icons.Outlined.FilterAlt,
                        contentDescription = stringResource(id = R.string.filter_cd)
                    )
                }
            }
        }
    }

    if (openFilterDialog.value) {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.filter_prompt_title))
            },
            onDismissRequest = {
                openFilterDialog.value = false
                selectedDateFilterOption = currentDateFilter.value
            },
            confirmButton = {
                TextButton(onClick = {
                    openFilterDialog.value = false
                    currentDateFilter.value = selectedDateFilterOption

                    if (isFindsPage) {
                        viewModel.findsDateFilter = currentDateFilter.value
                    } else {
                        viewModel.dateFilter = currentDateFilter.value
                    }

                    if (currentCoinTypeFilter != null) {
                        currentCoinTypeFilter.value = selectedCoinTypeFilterOption
                        viewModel.coinTypeFilter = currentCoinTypeFilter.value
                        filterActive.value =
                            !(currentDateFilter.value == DateFilter.UNSET && currentCoinTypeFilter.value == null)
                    } else {
                        filterActive.value = currentDateFilter.value != DateFilter.UNSET
                    }
                }){
                    Text(text = stringResource(id = R.string.save_prompt))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openFilterDialog.value = false
                    selectedDateFilterOption = currentDateFilter.value
                    selectedCoinTypeFilterOption = currentCoinTypeFilter?.value
                }) {
                    Text(text = stringResource(id = R.string.cancel_prompt))
                }
            },
            text = {
                Column {
                    var dateExpanded by remember { mutableStateOf(false) }
                    var coinTypeExpanded by remember { mutableStateOf(false) }

                    Row {
                        ExposedDropdownMenuBox(
                            expanded = dateExpanded,
                            onExpandedChange = {
                                dateExpanded = !dateExpanded
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 4.dp)
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
                                label = {
                                    Text(text = stringResource(id = R.string.date_from_ti_label))
                                },
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
                    }

                    if (currentCoinTypeFilter != null) {
                        Row {
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        Row {
                            ExposedDropdownMenuBox(
                                expanded = coinTypeExpanded,
                                onExpandedChange = {
                                    coinTypeExpanded = !coinTypeExpanded
                                },
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(start = 4.dp)
                            ) {
                                TextField(
                                    readOnly = true,
                                    value = TextFieldValue(
                                        if (selectedCoinTypeFilterOption == null) stringResource(id = R.string.not_set_option)
                                        else selectedCoinTypeFilterOption!!.name
                                    ),
                                    onValueChange = { },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = coinTypeExpanded
                                        )
                                    },
                                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                    label = {
                                        Text(text = stringResource(id = R.string.coin_type_ti_label))
                                    },
                                )
                                ExposedDropdownMenu(
                                    expanded = coinTypeExpanded,
                                    onDismissRequest = {
                                        coinTypeExpanded = false
                                    }
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedCoinTypeFilterOption = null
                                            coinTypeExpanded = false
                                        }
                                    ) {
                                        Text(text = stringResource(id = R.string.not_set_option))
                                    }
                                    allCoinTypes?.forEach {
                                        if (viewModel.isSupportedCoinType(it)) {
                                            DropdownMenuItem(
                                                onClick = {
                                                    selectedCoinTypeFilterOption = it
                                                    coinTypeExpanded = false
                                                }
                                            ) {
                                                val region = if (it.regionId == 1) {
                                                    stringResource(id = R.string.flag_ca)
                                                } else {
                                                    stringResource(id = R.string.flag_us)
                                                }
                                                Text(text = "$region ${it.name}")
                                            }
                                        }
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