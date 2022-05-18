package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RollsCard() {
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
            FormLabel(text = "No. Of Rolls", icon = Icons.Filled.Calculate)
            Row {
                Text(text = "1 cents: ")
                Text(text = "Select")
            }
            Row {
                Text(text = "5 cents: ")
                Text(text = "Select")
            }
            Row {
                Text(text = "10 cents: ")
                Text(text = "Select")
            }
        }
    }
}