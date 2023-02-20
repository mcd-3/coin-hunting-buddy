package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

private const val HUNTS_INDEX = 0

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: MainActivityViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val currentHuntGroup = viewModel.getCurrentHuntGroup()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "Details",
            scaffoldState = scaffoldState,
            navController = navController,
            isPopable = true
        ) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()) {
            Text(text = currentHuntGroup.toString())}
    }
}