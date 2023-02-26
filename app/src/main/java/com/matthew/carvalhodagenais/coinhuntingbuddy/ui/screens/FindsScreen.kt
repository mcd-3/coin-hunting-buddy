package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NoItemsWarning
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

private const val FINDS_INDEX = 1

@Composable
fun FindsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val allFinds by viewModel.getAllFinds().observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(title = "Finds", scaffoldState = scaffoldState) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = FINDS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {

        if (allFinds == null) {
            // Loading
        } else if (allFinds!!.isEmpty()) {
            NoItemsWarning(topText = "No finds", bottomText = "Start a hunt to add some!")
        } else if (allFinds!!.isNotEmpty()) {
            Column {
                allFinds!!.forEach { find ->
                    Text(text = find.year.toString())
                }
            }
        }
    }
}