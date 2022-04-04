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

@Composable
fun NavDrawer(scaffoldState: ScaffoldState) {
    Column {
        // TODO: Add image here
        NavOption(
            title = "Hunts",
            icon = Icons.Filled.Face,
            scaffoldState = scaffoldState
        )
        Divider()
        NavOption(
            title = "Finds",
            icon = Icons.Filled.Build,
            scaffoldState = scaffoldState
        )
        Divider()
        NavOption(
            title = "About",
            icon = Icons.Filled.Info,
            scaffoldState = scaffoldState
        )
        Divider()
        NavOption(
            title = "Settings",
            icon = Icons.Filled.Settings,
            scaffoldState = scaffoldState
        )
    }
}