package com.matthew.carvalhodagenais.coinhuntingbuddy.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.FindsScreen
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.HuntsScreen
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.Screen

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Hunts.route
    ) {
        composable(
            route = Screen.Hunts.route
        ) {
            HuntsScreen()
        }
        composable(
            route = Screen.Finds.route
        ) {
            FindsScreen()
        }
    }
}