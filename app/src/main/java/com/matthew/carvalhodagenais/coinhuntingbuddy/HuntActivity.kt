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
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.CoinTypeHuntPanel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.FullButton
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
                intent.getSerializableExtra("COIN_LIST") as HashMap<String, Int>
        ).filterValues { it != 0 }

        setContent {
            CoinHuntingBuddyTheme {
                val systemUiController = rememberSystemUiController()

                // Keys of the main list
                val firstKey = coinList.keys.first().toString()
                val lastKey = coinList.keys.last().toString()
                val selectedKey = remember { mutableStateOf(firstKey) }

                // Use this list to remove rolls from
                val tempCoinList = coinList.toMutableMap()
                val currentRollAmount = remember {
                    mutableStateOf(tempCoinList[selectedKey.value] as Int)
                }
                val completeHuntFlag = remember { mutableStateOf(tempCoinList.all { it.value == 0 }) }

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
                            title = { Text(text = if (region == "US") "American Coin Hunt" else "Canadian Coin Hunt") },
                            elevation = 0.dp
                        )

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .padding(start = 20.dp, end = 20.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            coinList.forEach {
                                TabButton(
                                    onClick = {
                                        currentRollAmount.value = tempCoinList[it.key] as Int
                                        selectedKey.value = it.key
                                    },
                                    text = MoneyStringToSymbolUtil.convert(it.key),
                                    leftIsRounded = firstKey == it.key,
                                    rightIsRounded = lastKey == it.key,
                                    key = it.key,
                                    selectedKey = selectedKey,
                                    modifier = Modifier.weight(getButtonWeight(coinList.size)))
                            }
                        }

                        CoinTypeHuntPanel(
                            coinKeyState = selectedKey,
                            rollsLeftState = currentRollAmount,
                            unwrapRollOnClick = {
                                tempCoinList.replace(
                                    selectedKey.value,
                                    tempCoinList[selectedKey.value] as Int - 1
                                )

                                currentRollAmount.value =
                                    tempCoinList[selectedKey.value] as Int

                                completeHuntFlag.value = tempCoinList.all { it.value == 0 }
                            }
                        )

                        // Remove this
                        coinList.forEach {
                            Text(text = "${it.key} : ${it.value}")
                        }

                        FullButton(
                            onClick = {
                                // Save all finds to DB
                                // Return to MainActivity
                            },
                            text = "Complete Hunt",
                            enabled = completeHuntFlag.value
                        )
                    }
                }
            }
        }
    }
}