package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder

@Composable
fun RegionCard(
    selectedRegion: String = "",
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
        Column {
            FormLabel(text = "Region", icon = Icons.Filled.Public)
            ToggleButtonGroup(
                options = arrayOf(
                    "Canada",
                    "U.S.A"
                ),
                isMultiSelect = false,
                selectedOption = selectedRegion,
                selectedOptionState = selectedRegionState
            )
        }
    }
}