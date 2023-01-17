package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel
import java.util.*

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
                    val rolls = viewModel.rollsPerCoin[coinType.name.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }]
                    Row() {
                        Text(text = "${coinType.name} - Rolls: $rolls")
                    }
                    listToDisplay.forEach {
                        Row() {
                            Text(text = "${it.year}${it.mintMark}")
                        }
                    }
                } else {
                    val capitalized = MoneyStringToSymbolUtil.singleToPlural(coinType.name).replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }
                    if (capitalized in viewModel.rollsPerCoin) {
                        val rolls = viewModel.rollsPerCoin[capitalized]
                        Row {
                            Text(text = "${coinType.name} - Rolls: $rolls")
                        }
                        Row {
                            Text(text = "No finds :(")
                        }
                    }
                }
            }
        }
    }
}