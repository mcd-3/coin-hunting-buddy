package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormLabel(text: String, icon: ImageVector) {
    val fontSize = 22.sp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 12.dp,
                top = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier
            .padding(
                end = 12.dp,
                start = 12.dp
            )
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color(0xFF353535),
                modifier = Modifier.size(22.dp)
            )
        }
        Column {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = fontSize,
                    color = Color(0xFF353535)
                ),
            )
        }
    }
}