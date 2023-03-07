package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegionCard(
    selectedRegionState: MutableState<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 10.dp
            ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.cardBackground
    ) {
        val caText = "\uD83C\uDDE8\uD83C\uDDE6 Canada"
        val usText = "\uD83C\uDDFA\uD83C\uDDF8 U.S.A"

        Column {
            FormLabel(text = "Region", icon = Icons.Filled.Public)

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
                            if (selectedRegionState.value == "Canada") caText
                            else usText
                        ),
                        onValueChange = { },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        label = { Text(text = "Region") },
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
                                selectedRegionState.value = "Canada"
                            }
                        ) {
                            Text(text = caText)
                        }
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedRegionState.value = "U.S.A"
                            }
                        ) {
                            Text(text = usText)
                        }
                    }
                }
            }
        }
    }
}