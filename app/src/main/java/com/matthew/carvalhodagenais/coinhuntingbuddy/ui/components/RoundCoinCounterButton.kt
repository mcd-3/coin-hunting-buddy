package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.addMinus
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

@Composable
fun RoundCoinCounterButton(
    onClick: () -> Unit,
    isIncrement: Boolean = true
) {
    val iconSize = 25.dp
    val buttonSize = 40.dp
    val borderStrokeSize = 2.dp
    val iconColor = MaterialTheme.colors.addMinus

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.size(buttonSize),
        shape = CircleShape,
        border = BorderStroke(
            borderStrokeSize,
            Color.DarkGray
        ),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color(0xFF6F6F6F))
    ) {
        if (isIncrement) {
            Icon(
                Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_cd),
                tint = iconColor,
                modifier = Modifier.size(iconSize),
            )
        } else {
            Icon(
                Icons.Filled.Remove,
                contentDescription = stringResource(id = R.string.subtract_cd),
                tint = iconColor,
                modifier = Modifier.size(iconSize)
            )
        }
    }
}