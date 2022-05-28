package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.states.ToggleButtonListState

// TODO: Set region using viewmodel
@Composable
fun RegionCard() {
    val toggleButtonListState = remember { ToggleButtonListState() }

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
            FormLabel(text = "Region", icon = Icons.Filled.Public)
            ToggleButtonGroup(
                options = arrayOf(
                    "Canada",
                    "U.S.A"
                ),
                isMultiSelect = false,
                toggleButtonListState = toggleButtonListState
            )
        }
    }
}