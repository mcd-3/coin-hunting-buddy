package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HalfBoldLabel(
    first: String,
    second: String,
    fontSize: Int,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        Text(text = first, fontWeight = FontWeight.Bold, fontSize = fontSize.sp)
        Text(text = second, fontSize = fontSize.sp)
    }
}
