package com.matthew.carvalhodagenais.coinhuntingbuddy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.*

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Hunts.route
    ) {
        composable(
            route = Screen.Hunts.route
        ) {
            HuntsScreen(navController = navHostController)
        }
        composable(
            route = Screen.Finds.route
        ) {
            FindsScreen(navController = navHostController)
        }
        composable(
            route = Screen.About.route
        ) {
            AboutScreen(navController = navHostController)
        }
        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(navController = navHostController)
        }
        composable(
            route = Screen.NewHunt.route
        ) {
            NewHuntScreen(navController = navHostController)   
        }
    }
}