package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
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
                title = { Text(text = "Summary") },
                elevation = 0.dp
            )
        },
    ) {
        // State
        val coinTypes = viewModel.getAllCoinTypes().observeAsState()

        Column() {
            val region = viewModel.getRegion()
            if (region == "CA") {
                Text(text = "Canadian Hunt")
            } else if (region == "US") {
                Text(text = "American Hunt")
            }

            coinTypes.value?.forEach { coinType ->
                val listToDisplay = mutableListOf<Find>()
                viewModel.listOfFinds.forEach { find ->
                    if (find.coinTypeId == coinType.id) {
                        listToDisplay.add(find)
                    }
                }

                if (listToDisplay.isNotEmpty()) {
                    Row() {
                        Text(text = "${coinType.name} - Rolls: X")
                    }
                    listToDisplay.forEach {
                        Row() {
                            Text(text = "${it.year}${it.mintMark}")
                        }
                    }
                }
                Log.d("CT", it.toString())
            }
        }
    }
}