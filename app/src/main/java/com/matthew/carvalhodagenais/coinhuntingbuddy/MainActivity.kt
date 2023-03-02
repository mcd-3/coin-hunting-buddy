package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.navigation.SetupNavGraph
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            CoinHuntingBuddyTheme {
                SetupNavGraph(navHostController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    CoinHuntingBuddyTheme {
        SetupNavGraph(navHostController = navController)
    }
}