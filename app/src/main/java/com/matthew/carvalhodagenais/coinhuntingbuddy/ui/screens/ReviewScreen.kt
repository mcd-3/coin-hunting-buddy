package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.FormLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.SummaryFinds
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel
import java.util.*

fun capitalize(str: String): String {
    return MoneyStringToSymbolUtil.singleToPlural(str).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

@Composable
fun ReviewScreen(
    viewModel: HuntActivityViewModel,
    navController: NavController,
) {

    val cardInnerPaddingStart = 10.dp
    val cardInnerPaddingEnd = 10.dp

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

        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = cardInnerPaddingStart,
                        end = cardInnerPaddingEnd,
                        bottom = 10.dp
                    )
                    .border(1.dp, Color(0xFFCECECE)),
                elevation = 10.dp
            ) {
                Column {
                    val region = if (viewModel.getRegion() == "CA") "Canada" else "United States of America"
                    FormLabel(text = "Hunt Details", icon = Icons.Filled.Notes)
                    Text(text = "Date Hunted: X")
                    Text(text = "Coin Region: $region")
                    Text(text = "Total Rolls: X")
                    Text(text = "Total Finds: X")
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = cardInnerPaddingStart,
                        end = cardInnerPaddingEnd,
                        bottom = 10.dp
                    )
                    .border(1.dp, Color(0xFFCECECE)),
                elevation = 10.dp
            ) {
                Column {
                    // This could be another card
                    FormLabel(text = "My Finds", icon = Icons.Filled.Public)

                    viewModel.rollsPerCoin.forEach { it
                        Log.e("ROLL", it.toString())
                    }

                    coinTypes.value?.forEach { it
                        Log.e("CT", it.toString())
                    }

                    // TODO: This is not optimal. Fix this implementation
                    val coinTypesFiltered = mutableListOf<CoinType>()
                    coinTypes.value?.forEach { ct ->
                        val capitalized = capitalize(ct.name)
                        viewModel.rollsPerCoin.forEach {
                            if (it.key == capitalized) {
                                coinTypesFiltered.add(ct)
                            }
                        }
                    }

                    coinTypesFiltered.forEach { coinType ->
                        val listToDisplay = mutableListOf<Find>()
                        viewModel.listOfFinds.forEach { find ->
                            if (find.coinTypeId == coinType.id) {
                                listToDisplay.add(find)
                            }
                        }

                        val capitalized = capitalize(coinType.name)
                        if (capitalized in viewModel.rollsPerCoin) {
                            SummaryFinds(
                                label = coinType.name,
                                rolls = viewModel.rollsPerCoin[capitalized],
                                listOfFinds = listToDisplay
                            )
                        }
                    }

//                    coinTypes.value?.forEach { coinType ->
//
//                        // For each rolls in rolls
//                        //   Look if coin type exists
//                        //   If so, add all the viewModel finds where id = id
//                        //   If no, remove this coin type from list
//
////                        val listToDisplay = mutableListOf<Find>()
////                        viewModel.listOfFinds.forEach { find ->
////                            if (find.coinTypeId == coinType.id) {
////                                listToDisplay.add(find)
////                            }
////                        }
////
////                        val capitalized = MoneyStringToSymbolUtil.singleToPlural(coinType.name).replaceFirstChar {
////                            if (it.isLowerCase()) it.titlecase(
////                                Locale.ROOT
////                            ) else it.toString()
////                        }
//                        if (capitalized in viewModel.rollsPerCoin) {
//                            val rolls = viewModel.rollsPerCoin[capitalized]
//                            Row {
//                                Text(text = "${coinType.name} - Rolls: $rolls")
//                            }
//
//                            if (listToDisplay.isNotEmpty()) {
//                                listToDisplay.forEach {
//                                    Row() {
//                                        Text(text = "${it.year}${it.mintMark}")
//                                    }
//                                }
//                            } else {
//                                Row {
//                                    Text(text = "No finds :(")
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }
}