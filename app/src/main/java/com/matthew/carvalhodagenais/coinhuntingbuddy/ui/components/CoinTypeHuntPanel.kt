package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun CoinTypeHuntPanel(
    regionCode: String,
    coinKeyState: MutableState<String>,
    rollsLeftState: MutableState<Int>,
    unwrapRollOnClick: () -> Unit,
//  listOfFinds?
) {
    Column {
        // Title
        Text(text = coinKeyState.value)
        Row() {
            Text(text = rollsLeftState.value.toString())
            Text(text = " rolls left")
        }
        Text(text = "region: $regionCode")
        // Amount of Coins Left
        // 2 Buttons:
        //    - Unwrap roll
        //    - New find
        // Find List
        Button(onClick = { unwrapRollOnClick() }) {
            Text(text = "Unwrap Roll")
        }
    }
}