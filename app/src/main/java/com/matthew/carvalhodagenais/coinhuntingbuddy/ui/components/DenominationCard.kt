package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DenominationCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
            .border(1.dp, Color(0xFFCECECE)),
        elevation = 10.dp
    ) {
        Column {
            FormLabel(text = "Denominations", icon = Icons.Filled.Paid)
            Row {
                ToggleButtonGroup(
                    options = arrayOf(
                        "1c",
                        "5c",
                        "10c",
                        "25c"
                    ),
                    isMultiSelect = true
                )
            }
            ToggleButtonGroup(
                options = arrayOf(
                    "Loonies",
                    "Toonies",
                ),
                isMultiSelect = true
            )
        }
    }
}