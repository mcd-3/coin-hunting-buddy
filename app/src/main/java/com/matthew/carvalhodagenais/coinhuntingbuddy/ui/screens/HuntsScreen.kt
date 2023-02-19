package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HuntGroupListItem
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NoItemsWarning
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.coroutineScope

private const val HUNTS_INDEX = 0

@Composable
fun HuntsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "Hunts",
            scaffoldState = scaffoldState,
            navController = navController,
            hasAddButton = true
        )},
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        val allHunts by viewModel.allHuntGroups.observeAsState()

        if (allHunts == null) {
            // Loading...
        } else if (allHunts!!.isEmpty()) {
            NoItemsWarning(topText = "No hunts", bottomText = "Click \"+\" to start one!")
        } else if (allHunts!!.isNotEmpty()) {
            Column {
                allHunts!!.forEach {
                    HuntGroupListItem(
                        huntGroup = it,
                        viewModel = viewModel,
                        onClick = {
                            // TODO: Go to a description page
                            Log.e("CLICK", "Row clicked! ID: ${it.id}")
                        }
                    )
                }
            }
        }
    }
}
