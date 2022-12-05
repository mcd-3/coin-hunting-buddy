package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel

@Composable
fun ReviewScreen(
    viewModel: HuntActivityViewModel,
    navController: NavController,
) {
    // Get list of finds from ViewModel
    // Get Region from ViewModel
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = { Text(text = "Your Finds") },
                elevation = 0.dp
            )
        },
    ) {
        Text(text = viewModel.getRegion())
        Log.d("FINDS", viewModel.listOfFinds.toString())
    }
}