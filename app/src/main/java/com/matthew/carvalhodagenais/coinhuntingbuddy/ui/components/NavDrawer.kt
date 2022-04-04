package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.Screen

private const val HUNTS_INDEX = 0
private const val FINDS_INDEX = 1
private const val ABOUT_INDEX = 2
private const val SETTINGS_INDEX = 3

@Composable
fun NavDrawer(
    scaffoldState: ScaffoldState,
    navController: NavController,
    selectedIndex: Int
) {
    Column {
        // TODO: Add image here
        NavOption(
            title = "Hunts",
            icon = Icons.Filled.Face,
            scaffoldState = scaffoldState,
            navController = navController,
            navRoute = Screen.Hunts.route,
            isSelected = (selectedIndex != HUNTS_INDEX)
        )
        Divider()
        NavOption(
            title = "Finds",
            icon = Icons.Filled.Build,
            scaffoldState = scaffoldState,
            navController = navController,
            navRoute = Screen.Finds.route,
            isSelected = (selectedIndex != FINDS_INDEX)
        )
        Divider()
        NavOption(
            title = "About",
            icon = Icons.Filled.Info,
            scaffoldState = scaffoldState,
            navController = navController,
            navRoute = Screen.About.route,
            isSelected = (selectedIndex != ABOUT_INDEX)
        )
        Divider()
        NavOption(
            title = "Settings",
            icon = Icons.Filled.Settings,
            scaffoldState = scaffoldState,
            navController = navController,
            navRoute = Screen.Settings.route,
            isSelected = (selectedIndex != SETTINGS_INDEX)
        )
    }
}