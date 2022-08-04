package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.sp

@Composable
fun FullButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean
) {
    Button(
        onClick = { onClick() },
        enabled = enabled,
        shape = RectangleShape,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text, fontSize = 24.sp)
    }
}