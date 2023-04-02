package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegionCard(
    selectedRegionState: MutableState<String>
) {
    val context = LocalContext.current
    LabelCard(
        label = stringResource(id = R.string.region_label),
        icon = Icons.Filled.Public
    ) {
        val caText = stringResource(
            id = R.string.region_with_flag,
            stringResource(id = R.string.flag_ca),
            stringResource(id = R.string.ca_region)
        )
        val usText = stringResource(
            id = R.string.region_with_flag,
            stringResource(id = R.string.flag_us),
            stringResource(id = R.string.us_region)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                TextField(
                    readOnly = true,
                    value = TextFieldValue(
                        if (selectedRegionState.value == stringResource(id = R.string.ca_region)) caText
                        else usText
                    ),
                    onValueChange = { },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    label = {
                        Text(text = stringResource(id = R.string.region_label))
                    },
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            selectedRegionState.value = context.getString(R.string.ca_region)
                        }
                    ) {
                        Text(text = caText)
                    }
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            selectedRegionState.value = context.getString(R.string.us_region)
                        }
                    ) {
                        Text(text = usText)
                    }
                }
            }
        }
    }
}