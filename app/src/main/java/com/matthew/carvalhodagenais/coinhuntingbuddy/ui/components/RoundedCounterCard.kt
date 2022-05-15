package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RoundedCounterCard(
    text: String,
    color: Color
) {
    val boxPadding = 10.dp
    val boxHeight = 135.dp
    val lineHeightOffset = 40f
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(boxHeight)
        .padding(boxPadding)
        .drawBehind {
            drawRect(
                color = color,
                size = Size(height = size.height - 40, width = size.width),
                style = Stroke(
                    width = 6.dp.toPx(),
                    pathEffect = PathEffect.cornerPathEffect(4.dp.toPx())
                ),
                topLeft = Offset(0f, lineHeightOffset),
            )
            drawLine(
                color = Color.White,
                start = Offset(x = size.width * 0.06f, y = lineHeightOffset),
                end = Offset(x = size.width * 0.40f, y = lineHeightOffset),
                strokeWidth = 25f
            )
        }
    ) {
        Column {
            Text(
                text = text,
                modifier = Modifier.offset(x = 35.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Row(
                modifier = Modifier
                    .padding(
                        top = 4.dp,
                        start = 24.dp,
                        end = 24.dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RoundCoinCounterButton(onClick = { })
                Text(text = "1")
                RoundCoinCounterButton(onClick = { }, false)
            }
        }
    }
}