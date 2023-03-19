package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar

private const val SETTINGS_INDEX = 3

@Composable
fun SettingsScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    // Back Handler to go back to the hunts screen
    BackHandler {
        navController.navigate("hunts_screen") {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.settings_screen),
                scaffoldState = scaffoldState
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = SETTINGS_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()) {
            Text(text = "settings")}
    }
}