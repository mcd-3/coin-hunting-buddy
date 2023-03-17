package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.MainActivity
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.topAppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.ArrayTools
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.HuntActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Determines the weight of TabButtons
 *
 * @param sizeOfMap Int - Size of map
 */
fun getButtonWeight(sizeOfMap: Int): Float {
    return when (sizeOfMap) {
        1 -> 1f
        2 -> 0.5f
        3 -> 0.33f
        4 -> 0.25f
        5 -> 0.2f
        6 -> 0.165f
        else -> 0.33f
    }
}

/**
 * Maps a map of coins' Key and Value to arrays
 *
 * PS: This function sucks
 *
 * @param map Map<String, Int> - Base map to use
 * @param region String - Region of the coins
 * @param keysArray Array<String> - Array of keys. Max size is 6
 * @param rollsArray<Int> - Array of rolls. Max size is 6
 * @param context Context - Application context used to get strings
 */
fun arrangeCoinMap(
    map: Map<String, Int>,
    region: String,
    keysArray: Array<String>,
    rollsArray: Array<Int>,
    context: Context
) {
    if (region == context.getString(R.string.us_region_code)) {
        map.forEach {
            when (it.key) {
                context.getString(R.string.us_penny_plural) -> {
                    keysArray[0] = context.getString(R.string.us_penny_plural)
                    rollsArray[0] = map[it.key]!!
                }
                context.getString(R.string.us_nickel_plural) -> {
                    keysArray[1] = context.getString(R.string.us_nickel_plural)
                    rollsArray[1] = map[it.key]!!
                }
                context.getString(R.string.us_dime_plural) -> {
                    keysArray[2] = context.getString(R.string.us_dime_plural)
                    rollsArray[2] = map[it.key]!!
                }
                context.getString(R.string.us_quarter_plural) -> {
                    keysArray[3] = context.getString(R.string.us_quarter_plural)
                    rollsArray[3] = map[it.key]!!
                }
                context.getString(R.string.us_hd_plural) -> {
                    keysArray[4] = context.getString(R.string.us_hd_plural)
                    rollsArray[4] = map[it.key]!!
                }
                context.getString(R.string.us_dollar_plural) -> {
                    keysArray[5] = context.getString(R.string.us_dollar_plural)
                    rollsArray[5] = map[it.key]!!
                }
            }
        }
    } else { // Canada
        map.forEach {
            when (it.key) {
                context.getString(R.string.ca_1c_plural) -> {
                    keysArray[0] = context.getString(R.string.ca_1c_plural)
                    rollsArray[0] = map[it.key]!!
                }
                context.getString(R.string.ca_5c_plural) -> {
                    keysArray[1] = context.getString(R.string.ca_5c_plural)
                    rollsArray[1] = map[it.key]!!
                }
                context.getString(R.string.ca_10c_plural) -> {
                    keysArray[2] = context.getString(R.string.ca_10c_plural)
                    rollsArray[2] = map[it.key]!!
                }
                context.getString(R.string.ca_25c_plural) -> {
                    keysArray[3] = context.getString(R.string.ca_25c_plural)
                    rollsArray[3] = map[it.key]!!
                }
                context.getString(R.string.ca_loonie_plural) -> {
                    keysArray[4] = context.getString(R.string.ca_loonie_plural)
                    rollsArray[4] = map[it.key]!!
                }
                context.getString(R.string.ca_toonie_plural) -> {
                    keysArray[5] = context.getString(R.string.ca_toonie_plural)
                    rollsArray[5] = map[it.key]!!
                }
            }
        }
    }
}

@Composable
fun HuntScreen(
    viewModel: HuntActivityViewModel,
    navController: NavController,
    region: String,
    coinList: Map<String, Int>,
) {
    // Use this list to remove rolls from
    val tempCoinList = coinList.toMutableMap()
    viewModel.rollsPerCoin = tempCoinList

    // Flags
    val showHuntCompleteDialog = remember { mutableStateOf(false)}
    val completeHuntFlag = remember { mutableStateOf(tempCoinList.all { it.value == 0 }) }

    // Context is needed here to go back to MainActivity
    val context = LocalContext.current

    val navString = stringResource(id = R.string.review_route)

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.topAppBar,
                title = {
                    Text(
                        text = if (region == stringResource(id = R.string.us_region_code)) {
                            stringResource(id = R.string.us_coin_hunt_label)
                        } else {
                            stringResource(id = R.string.ca_coin_hunt_label)
                        }
                    )
                },
                elevation = 0.dp
            )
        },
    ) {
        val showAlertDialog = remember { mutableStateOf(false) }
        BackHandler {
            showAlertDialog.value = true
        }
        if(showAlertDialog.value){
            ConfirmCancelAlertDialog(
                title = stringResource(id = R.string.stop_hunt_confirm_label),
                body = stringResource(id = R.string.stop_hunt_warning_label),
                confirmLabel = stringResource(id = R.string.yes_prompt),
                cancelLabel = stringResource(id = R.string.no_prompt),
                toggledState = showAlertDialog,
                onConfirm = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                },
                onCancel = { showAlertDialog.value = false }
            )
        }

        Column {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(10.dp))

            Column(modifier = Modifier.weight(0.7f)) {

                val selectedKey = remember { mutableStateOf("") }
                val currentRollAmount = remember {
                    mutableStateOf(0)
                }

                Row(     
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // First, we need to arrange the list
                    val keys = arrayOf("", "", "", "", "", "")
                    val rolls = arrayOf(-1, -1, -1, -1, -1, -1)

                    arrangeCoinMap(coinList, region, keys, rolls, context)

                    keys.forEach {
                        Log.e("KEYS_BRUH", it)
                    }

                    val firstKey = keys[
                        ArrayTools.firstIndexWhereNot(rolls, -1)!!
                    ]

                    var lastIndex = 0
                    for (i in 5 downTo 0 step 1) {
                        if (rolls[i] != -1) {
                            lastIndex = i
                            break
                        }
                    }

                    val lastKey = keys[lastIndex]

                    if (selectedKey.value.isEmpty()) {
                        selectedKey.value = firstKey
                        currentRollAmount.value = tempCoinList[selectedKey.value]!!
                    }

                    keys.forEachIndexed { index, it ->
                        if (it != "") {
                            TabButton(
                                onClick = {
                                    selectedKey.value = keys[index]
                                    currentRollAmount.value = tempCoinList[selectedKey.value]!!
                                },
                                text = MoneyStringToSymbolUtil.convert(keys[index], context),
                                leftIsRounded = firstKey == keys[index],
                                rightIsRounded = lastKey == keys[index],
                                key = keys[index],
                                selectedKey = selectedKey,
                                modifier = Modifier.weight(getButtonWeight(coinList.size))
                            )
                        }
                    }
                }

                CoinTypeHuntPanel(
                    coinKeyState = selectedKey,
                    rollsLeftState = currentRollAmount,
                    viewModel = viewModel,
                    unwrapRollOnClick = {
                        val rollsMinusOne = tempCoinList[selectedKey.value] as Int - 1
                        tempCoinList.replace(
                            selectedKey.value,
                            rollsMinusOne
                        )

                        currentRollAmount.value = rollsMinusOne
                        completeHuntFlag.value = tempCoinList.all { it.value == 0 }
                    }
                )
            }

            if(showHuntCompleteDialog.value){
                ConfirmCancelAlertDialog(
                    title = stringResource(id = R.string.complete_hunt_confirm_label),
                    body = stringResource(id = R.string.complete_hunt_warning_label),
                    confirmLabel = stringResource(id = R.string.complete_prompt),
                    cancelLabel = stringResource(id = R.string.cancel_prompt),
                    toggledState = showHuntCompleteDialog,
                    onConfirm = {
                        viewModel.setRegion(region)
                        showHuntCompleteDialog.value = false
                        navController.popBackStack()
                        navController.navigate(navString)

                        // We need GlobalScope here since the activity can be changed
                        // by the user before saving has completed
                        GlobalScope.launch {
                            viewModel.saveData()
                        }
                    },
                    onCancel = { showHuntCompleteDialog.value = false }
                )
            }
            Row(Modifier.weight(0.1f)) {
                FullButton(
                    onClick = {
                        showHuntCompleteDialog.value = true
                    },
                    text = stringResource(id = R.string.complete_btn),
                    enabled = completeHuntFlag.value
                )
            }
        }
    }
}