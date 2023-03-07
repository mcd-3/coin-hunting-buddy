package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground

@Composable
fun RollsCard(stateMap: Map<String, MutableState<TextFieldValue>>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
            .border(1.dp, Color(0xFFCECECE)),
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.cardBackground
    ) {
        Column {
            FormLabel(text = "No. Of Rolls", icon = Icons.Filled.Calculate)
            stateMap.forEach{ item ->
                CounterRow(label = item.key, mutVal = item.value)

                if (item.key != "Toonies" && item.key != "Dollars") {
                    Divider(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp, start = 14.dp, end = 14.dp))
                } else {
                    Divider(
                        modifier = Modifier.padding(top = 12.dp),
                        color = MaterialTheme.colors.cardBackground
                    )
                }
            }
        }
    }
}