package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun RollsCard() {
    val pennyState = remember { mutableStateOf(TextFieldValue(text = "0")) }
    val nickelState = remember { mutableStateOf(TextFieldValue(text = "0")) }
    val dimeState = remember { mutableStateOf(TextFieldValue(text = "0")) }
    val quarterState = remember { mutableStateOf(TextFieldValue(text = "0")) }
    val loonieState = remember { mutableStateOf(TextFieldValue(text = "0")) }
    val toonieState = remember { mutableStateOf(TextFieldValue(text = "0")) }

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
            CounterRow(label = "1 Cents", mutVal = pennyState)
            CounterRow(label = "5 Cents", mutVal = nickelState)
            CounterRow(label = "10 Cents", mutVal = dimeState)
            CounterRow(label = "25 Cents", mutVal = quarterState)
            CounterRow(label = "Loonies", mutVal = loonieState)
            CounterRow(label = "Toonies", mutVal = toonieState)
        }
    }
}