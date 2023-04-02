package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.MainActivity
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HalfBoldLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.LabelCard
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.SummaryFinds
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.topAppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel
import java.util.*

fun capitalize(str: String, context: Context): String {
    return MoneyStringToSymbolUtil.singleToPlural(str, context).replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

/**
 * Sends the user back to the main activity
 *
 * @param context Context
 */
private fun goToMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
}

@Composable
fun ReviewScreen(
    viewModel: HuntActivityViewModel,
    navController: NavController,
) {
    val context = LocalContext.current

    // Back Handler to go back to the main activity
    BackHandler {
        goToMainActivity(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.topAppBar,
                title = {
                    Text(text = stringResource(id = R.string.review_screen))
                },
                elevation = 0.dp,
                actions = {
                    IconButton(
                        onClick = {
                            goToMainActivity(context)
                        }
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            stringResource(id = R.string.check_icon_cd),
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
                LabelCard(
                    label = stringResource(id = R.string.hunt_overview_label),
                    icon = Icons.Filled.Notes
                ) {
                    val region = if (
                        viewModel.getRegion() == stringResource(id = R.string.ca_region_code)
                    ) {
                        stringResource(id = R.string.ca_region)
                    } else {
                        stringResource(id = R.string.us_region)
                    }

                    val fontSize = 14
                    val halfLabelModifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 4.dp
                    )

                    HalfBoldLabel(
                        first = stringResource(id = R.string.date_hunted_half_label),
                        second = viewModel.dateAsString(),
                        fontSize = fontSize,
                        modifier = halfLabelModifier
                    )
                    HalfBoldLabel(
                        first = stringResource(id = R.string.coin_region_half_label),
                        second = region,
                        fontSize = fontSize,
                        modifier = halfLabelModifier
                    )
                    HalfBoldLabel(
                        first = stringResource(id = R.string.total_rolls_half_label),
                        second = viewModel.getRollCount().toString(),
                        fontSize = fontSize,
                        modifier = halfLabelModifier
                    )
                    HalfBoldLabel(
                        first = stringResource(id = R.string.total_finds_half_label),
                        second = viewModel.listOfFinds.size.toString(),
                        fontSize = fontSize,
                        modifier = halfLabelModifier
                    )
                }

                LabelCard(
                    label = stringResource(id = R.string.my_finds_label),
                    icon = Icons.Filled.Public
                ) {
                    coinTypes.value?.forEach { coinType ->
                        val listToDisplay = mutableListOf<Find>()
                        viewModel.listOfFinds.forEach { find ->
                            if (find.coinTypeId == coinType.id) {
                                listToDisplay.add(find)
                            }
                        }

                        val capitalized = capitalize(coinType.name, LocalContext.current)
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