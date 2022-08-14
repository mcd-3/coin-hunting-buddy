package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoinTypeHuntPanel(
    coinKeyState: MutableState<String>,
    rollsLeftState: MutableState<Int>,
    unwrapRollOnClick: () -> Unit,
//  listOfFinds?
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 10.dp,
                end = 10.dp,
                bottom = 10.dp
            )
            .border(1.dp, Color(0xFFCECECE)),
        elevation = 10.dp
    ) {
        Column {
            Text(
                text = coinKeyState.value,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color(0xFF353535)
                ),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.55f)
                        .height(52.dp),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = rollsLeftState.value.toString(),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 42.sp,
                            color = Color(0xFF353535)
                        ),
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .weight(0.7f)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(0.45f)
                        .height(52.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(text = " rolls left")
                }
            }
            Row(modifier = Modifier.padding(top = 32.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { unwrapRollOnClick() },
                        enabled = rollsLeftState.value > 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 18.dp),
                    ) {
                        Text(text = "Unwrap Roll")
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .height(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 32.dp),
                    ) {
                        Text(text = "New Find")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            FindsPanel()

            // Find List
            Text(text = "List of Finds goes here")
        }
    }
}