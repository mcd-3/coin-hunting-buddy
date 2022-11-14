package com.matthew.carvalhodagenais.coinhuntingbuddy.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.*

@Composable
fun SetupSecondaryNavGraph(
    navHostController: NavHostController,
    region: String,
    coinList: Map<String, Int>,
    listOfFinds: MutableList<Find>,
    huntDoneFlag: MutableState<Boolean>
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Hunt.route
    ) {
        composable(
            route = Screen.Hunt.route
        ) {
            HuntScreen(
                navHostController,
                region,
                coinList,
                listOfFinds,
                huntDoneFlag
            )
        }
        composable(
            route = Screen.Review.route
        ) {
            ReviewScreen(navController = navHostController)
        }
    }
}