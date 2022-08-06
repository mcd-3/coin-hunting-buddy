package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.TabButton
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil

fun getButtonWeight(sizeOfMap: Int): Float {
    return when (sizeOfMap) {
        1 -> 1f
        2 -> 0.5f
        3 -> 0.33f
        4 -> 0.25f
        5 -> 0.2f
        6 -> 0.165f
        else -> 0.33f
    }
}

class HuntActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val region = intent.getStringExtra("COIN_REGION")
        val coinList = (
                intent.getSerializableExtra("COIN_LIST") as HashMap<*, *>
        ).filterValues { it != 0 }

        setContent {
            CoinHuntingBuddyTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.LightGray
                    )
                }
                Surface {

                    val showAlertDialog = remember { mutableStateOf(false)}
                    BackHandler {
                        showAlertDialog.value = true
                    }
                    if(showAlertDialog.value){
                        AlertDialog(
                            onDismissRequest = { /* Do nothing */ },
                            confirmButton = {
                                TextButton(onClick = {
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }){
                                    Text(text = "Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showAlertDialog.value = false }) {
                                    Text(text = "No")
                                }
                            },
                            title = { Text(text = "Are you sure you want to stop this hunt?") },
                            text = { Text(text = "This hunt and any finds will not be saved.") }
                        )
                    }


                    Column {
                        TopAppBar(
                            backgroundColor = Color.White,
                            title = { Text(text = "Coin Hunt") }
                        )
                        Text(text = region.toString())

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .padding(start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            val first = coinList.keys.first().toString()
                            val last = coinList.keys.last().toString()
                            val selectedKey = remember { mutableStateOf(first) }
                            coinList.forEach {
                                TabButton(
                                    onClick = {
                                        selectedKey.value = it.key.toString()
                                    },
                                    text = MoneyStringToSymbolUtil.convert(it.key.toString()),
                                    leftIsRounded = first == it.key.toString(),
                                    rightIsRounded = last == it.key.toString(),
                                    key = it.key.toString(),
                                    selectedKey = selectedKey,
                                    modifier = Modifier.weight(getButtonWeight(coinList.size)))
                            }
                        }

                        coinList.forEach {
                            Text(text = "${it.key} : ${it.value}")
                        }
                    }
                }
            }
        }
    }
}