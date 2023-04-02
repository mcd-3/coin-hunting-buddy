package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FindAddEditDialog(
    showAlertDialog: MutableState<Boolean>,
    gradeCodesList: List<String>,
    gradeIndex: Int = 0,
    yearStringState: MutableState<String>,
    varietyStringState: MutableState<String>,
    mintMarkStringState: MutableState<String>,
    gradeStringState: MutableState<String>,
    errorStringState: MutableState<String>,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    if (showAlertDialog.value){
        AlertDialog(
            onDismissRequest = {
                onCancel()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                    }
                ){
                    Text(text = stringResource(id = R.string.done_prompt))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onCancel()
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel_prompt))
                }
            },
            title = { Text(text = stringResource(id = R.string.new_find_prompt_title)) },
            text = {
                Column {
                    Row {
                        OutlinedTextField(
                            value = yearStringState.value,
                            label = {
                                Text(
                                    text = stringResource(id = R.string.year_ti_label)
                                )
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.year_ti_placeholder)
                                )
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(end = 4.dp)
                                .align(Alignment.Top),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = {
                                val maxCharLength = 4
                                if (it.length <= maxCharLength && !it.contains("-", true)) {
                                    if (it.length > 1 && it[0] == '0') {
                                        val newStr = it.drop(1)
                                        yearStringState.value = newStr
                                    } else {
                                        yearStringState.value = it

                                        if (it.contains(" ")) {
                                            yearStringState.value = it.replace(" ", "")
                                        }

                                        if (it.contains(".")) {
                                            yearStringState.value = it.replace(".", "")
                                        }

                                        if (it.contains(",")) {
                                            yearStringState.value = it.replace(",", "")
                                        }
                                    }
                                }

                                if (yearStringState.value.isBlank()) {
                                    yearStringState.value = "0"
                                }
                            }
                        )

                        var expanded by remember { mutableStateOf(false) }
                        var selectedOption by remember { mutableStateOf(gradeCodesList[gradeIndex]) }
                        gradeStringState.value = selectedOption
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
                                value = TextFieldValue(selectedOption),
                                onValueChange = { },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                label = {
                                    Text(text = stringResource(id = R.string.grade_ti_label))
                                },
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                gradeCodesList.forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOption = it
                                            expanded = false
                                            gradeStringState.value = selectedOption
                                        }
                                    ) {
                                        Text(text = it)
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = mintMarkStringState.value,
                        label = {
                            Text(text = stringResource(id = R.string.mm_ti_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.mm_ti_placeholder))
                        },
                        onValueChange = {
                            mintMarkStringState.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = varietyStringState.value,
                        label = {
                            Text(text = stringResource(id = R.string.variety_ti_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.variety_ti_placeholder))
                        },
                        onValueChange = {
                            varietyStringState.value = it
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = errorStringState.value,
                        label = {
                            Text(text = stringResource(id = R.string.error_ti_label))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.error_ti_placeholder))
                        },
                        onValueChange = {
                            errorStringState.value = it
                        }
                    )
                }
            }
        )
    }
}