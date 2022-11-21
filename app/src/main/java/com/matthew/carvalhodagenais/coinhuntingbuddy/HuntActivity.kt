package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matthew.carvalhodagenais.coinhuntingbuddy.navigation.SetupSecondaryNavGraph
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme

class HuntActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var navController: NavHostController

        val region = intent.getStringExtra("COIN_REGION")
        val coinList = (
                intent.getSerializableExtra("COIN_LIST") as HashMap<String, Int>
        ).filterValues { it != 0 }

        setContent {
            navController = rememberNavController()
            CoinHuntingBuddyTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.LightGray
                    )
                }

                SetupSecondaryNavGraph(
                    navController,
                    region!!,
                    coinList
                )
            }
        }
    }
}