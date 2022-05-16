package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.RoundedCounterCard

private const val HUNTS_INDEX = 0

@Composable
fun NewHuntScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "New Hunt",
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
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            RoundedCounterCard(text = "1 Cent Rolls", color = Color.Red)
            RoundedCounterCard(text = "5 Cent Rolls", color = Color.Blue)
            RoundedCounterCard(text = "10 Cent Rolls", color = Color.Green)
            RoundedCounterCard(text = "25 Cent Rolls", color = Color(0xFF6F6F6F))
            RoundedCounterCard(text = "Loonie Rolls", color = Color.Black)
            RoundedCounterCard(text = "Toonie Rolls", color = Color.Magenta)
        }
    }
}
