package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Filter(
    filterActive: MutableState<Boolean>,
    openFilterDialog: MutableState<Boolean>,
    currentDateFilter: MutableState<DateFilter>,
    coroutineScope: CoroutineScope,
) {
    var selectedDateFilterOption by remember { mutableStateOf(currentDateFilter.value) }

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
                    filterActive.value = currentDateFilter.value != DateFilter.UNSET
                }){
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openFilterDialog.value = false
                    selectedDateFilterOption = currentDateFilter.value
                }) {
                    Text(text = "Cancel")
                }
            },
            text = {
                Row {
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
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
                                    expanded = expanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            label = { Text(text = "Date From") },
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            DateFilter.values().forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        selectedDateFilterOption = it
                                        expanded = false
                                    }
                                ) {
                                    Text(text = it.dateFilter)
                                }
                            }
                        }
                    }
                }
            },
        )
    }
}