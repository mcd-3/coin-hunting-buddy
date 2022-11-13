package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme

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
                val context = LocalContext.current

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.LightGray
                    )
                }
                HuntBox(region!!, coinList)
            }
        }
    }
}