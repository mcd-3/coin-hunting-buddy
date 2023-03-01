package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.unselected

@Composable
fun TabButton(
    onClick: () -> Unit,
    text: String,
    key: String,
    selectedKey: MutableState<String>,
    leftIsRounded: Boolean = false,
    rightIsRounded: Boolean = false,
    modifier: Modifier
) {
    val leftRoundDP = if (leftIsRounded) 6.dp else 0.dp
    val rightRoundDP = if (rightIsRounded) 6.dp else 0.dp

    val selectedColor = MaterialTheme.colors.primary
    val unselectedColor = MaterialTheme.colors.unselected

    val btnColour = remember { mutableStateOf(
        if (selectedKey.value == key) selectedColor else unselectedColor
    ) }

    Button(
        onClick = {
            onClick()
            selectedKey.value = key
            btnColour.value = selectedColor
        },
        shape = RoundedCornerShape(
            topStart = leftRoundDP,
            topEnd = rightRoundDP
        ),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedKey.value == key) selectedColor else unselectedColor),
    ) {
        Text(text = text, color = if (selectedKey.value == key) Color.White else Color.Black)
    }
}