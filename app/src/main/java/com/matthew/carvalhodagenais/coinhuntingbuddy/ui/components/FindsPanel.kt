package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FindsPanel(
    // findsList: List<String>, //TODO: Change my type!
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
                    .background(Color.Gray)
            )
            Text(text = "Finds")
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
                    .background(Color.Gray)
            )
        }
    }
}