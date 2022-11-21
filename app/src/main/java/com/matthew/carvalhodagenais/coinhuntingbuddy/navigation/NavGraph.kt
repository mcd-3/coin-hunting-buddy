package com.matthew.carvalhodagenais.coinhuntingbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

@Composable
fun SetupNavGraph(navHostController: NavHostController) {
    val viewModel: MainActivityViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Hunts.route
    ) {
        composable(
            route = Screen.Hunts.route
        ) {
            HuntsScreen(
                viewModel = viewModel,
                navController = navHostController
            )
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