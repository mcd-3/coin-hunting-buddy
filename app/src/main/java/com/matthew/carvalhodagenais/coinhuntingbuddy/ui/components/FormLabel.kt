package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 12.dp,
                top = 12.dp,
                start = 20.dp,
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .padding(
                    end = 12.dp,
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
        Divider(
            color = Color(0xFF353535),
            thickness = 2.dp,
            modifier = Modifier.padding(top = 8.dp, end = 20.dp, bottom = 8.dp)
        )
    }
}