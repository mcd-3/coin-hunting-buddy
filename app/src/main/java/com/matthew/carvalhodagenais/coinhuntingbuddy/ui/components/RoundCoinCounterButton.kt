package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundCoinCounterButton(
    onClick: () -> Unit,
    isIncrement: Boolean = true
) {
    val iconSize = 35.dp
    val buttonSize = 70.dp
    val iconColor = Color.DarkGray

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(buttonSize),
        shape = CircleShape,
        border = BorderStroke(
            4.dp,
            Color.DarkGray
        ),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.DarkGray)
    ) {
        if (isIncrement) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Add",
                tint = iconColor,
                modifier = Modifier.size(iconSize),
            )
        } else {
            Icon(
                Icons.Filled.Remove,
                contentDescription = "Subtract",
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}