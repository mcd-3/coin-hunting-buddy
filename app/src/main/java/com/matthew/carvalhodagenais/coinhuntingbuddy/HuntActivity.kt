package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme

class HuntActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val region = intent.getStringExtra("COIN_REGION")
        val coinList = intent.getSerializableExtra("COIN_LIST") as HashMap<*, *>

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

                        coinList.forEach {
                            Text(text = "${it.key} : ${it.value}")
                        }
                    }
                }
            }
        }
    }
}