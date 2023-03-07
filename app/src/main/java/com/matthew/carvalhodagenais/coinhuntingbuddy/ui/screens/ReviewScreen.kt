package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.MainActivity
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.FormLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HalfBoldLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.SummaryFinds
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.topAppBar
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
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.topAppBar,
                title = { Text(text = "Summary") },
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            val intent = Intent(context, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            "Done",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            )
        }
    ) {
        val coinTypes = viewModel.getAllCoinTypes().observeAsState()

        Column(
            modifier = Modifier.padding(start = 2.dp, end = 2.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = false)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = cardInnerPaddingStart,
                            end = cardInnerPaddingEnd,
                            bottom = 10.dp
                        ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
                    elevation = 4.dp,
                    backgroundColor = MaterialTheme.colors.cardBackground
                ) {
                    Column(modifier = Modifier.padding(bottom = 12.dp)) {
                        val region = if (viewModel.getRegion() == "CA") "Canada" else "United States of America"
                        FormLabel(text = "Hunt Overview", icon = Icons.Filled.Notes)

                        val fontSize = 14
                        val halfLabelModifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 4.dp
                        )

                        HalfBoldLabel(
                            first = "Date Hunted: ",
                            second = viewModel.dateAsString(),
                            fontSize = fontSize,
                            modifier = halfLabelModifier
                        )
                        HalfBoldLabel(
                            first = "Coin Region: ",
                            second = region,
                            fontSize = fontSize,
                            modifier = halfLabelModifier
                        )
                        HalfBoldLabel(
                            first = "Total Rolls: ",
                            second = viewModel.getRollCount().toString(),
                            fontSize = fontSize,
                            modifier = halfLabelModifier
                        )
                        HalfBoldLabel(
                            first = "Total Finds: ",
                            second = viewModel.listOfFinds.size.toString(),
                            fontSize = fontSize,
                            modifier = halfLabelModifier
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = cardInnerPaddingStart,
                            end = cardInnerPaddingEnd,
                            bottom = 10.dp
                        ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
                    elevation = 4.dp,
                    backgroundColor = MaterialTheme.colors.cardBackground
                ) {
                    Column {
                        // This could be another card
                        FormLabel(text = "My Finds", icon = Icons.Filled.Public)

                        coinTypes.value?.forEach { coinType ->
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
                    }
                }
            }
        }
    }
}