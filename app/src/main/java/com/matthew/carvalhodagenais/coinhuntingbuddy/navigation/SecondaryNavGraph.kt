package com.matthew.carvalhodagenais.coinhuntingbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

@Composable
fun SetupSecondaryNavGraph(
    navHostController: NavHostController,
    region: String,
    coinList: Map<String, Int>,
    listOfFinds: MutableList<Find>
) {

    val viewModel: HuntActivityViewModel = viewModel()

    NavHost(
        navController = navHostController,
        startDestination = Screen.Hunt.route
    ) {
        composable(
            route = Screen.Hunt.route
        ) {
            HuntScreen(
                viewModel = viewModel,
                navController = navHostController,
                region = region,
                coinList = coinList,
                listOfFinds = listOfFinds,
            )
        }
        composable(
            route = Screen.Review.route
        ) {
            ReviewScreen(viewModel = viewModel, navController = navHostController)
        }
    }
}