package com.matthew.carvalhodagenais.coinhuntingbuddy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.CoinTypeHuntPanel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.ConfirmCancelAlertDialog
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.FullButton
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.TabButton
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.CoinHuntingBuddyTheme
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.ArrayTools
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.MoneyStringToSymbolUtil

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
 * @param map Map<String, Int> - Base map to use
 * @param region String - Region of the coins
 * @param keysArray Array<String> - Array of keys. Max size is 6
 * * @param rollsArray<Int> - Array of rolls. Max size is 6
 */
fun arrangeCoinMap(
    map: Map<String, Int>,
    region: String,
    keysArray: Array<String>,
    rollsArray: Array<Int>
) {
    if (region == "US") {
        map.forEach {
            when (it.key) {
                "Pennies" -> {
                  keysArray[0] = "Pennies"
                  rollsArray[0] = map[it.key]!!
                }
                "Nickels" -> {
                    keysArray[1] = "Nickels"
                    rollsArray[1] = map[it.key]!!
                }
                "Dimes" -> {
                    keysArray[2] = "Dimes"
                    rollsArray[2] = map[it.key]!!
                }
                "Quarters" -> {
                    keysArray[3] = "Quarters"
                    rollsArray[3] = map[it.key]!!
                }
                "Half-Dollars" -> {
                    keysArray[4] = "Half-Dollars"
                    rollsArray[4] = map[it.key]!!
                }
                "Dollars" -> {
                    keysArray[5] = "Dollars"
                    rollsArray[5] = map[it.key]!!
                }
            }
        }
    } else { // Canada
        map.forEach {
            when (it.key) {
                "1 Cents" -> {
                    keysArray[0] = "1 Cents"
                    rollsArray[0] = map[it.key]!!
                }
                "5 Cents" -> {
                    keysArray[1] = "5 Cents"
                    rollsArray[1] = map[it.key]!!
                }
                "10 Cents" -> {
                    keysArray[2] = "10 Cents"
                    rollsArray[2] = map[it.key]!!
                }
                "25 Cents" -> {
                    keysArray[3] = "25 Cents"
                    rollsArray[3] = map[it.key]!!
                }
                "Loonies" -> {
                    keysArray[4] = "Loonies"
                    rollsArray[4] = map[it.key]!!
                }
                "Toonies" -> {
                    keysArray[5] = "Toonies"
                    rollsArray[5] = map[it.key]!!
                }
            }
        }
    }
}

class HuntActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val region = intent.getStringExtra("COIN_REGION")
        val coinList = (
                intent.getSerializableExtra("COIN_LIST") as HashMap<String, Int>
        ).filterValues { it != 0 }

        setContent {
            CoinHuntingBuddyTheme {
                val systemUiController = rememberSystemUiController()
                val context = LocalContext.current

                // Use this list to remove rolls from
                val tempCoinList = coinList.toMutableMap()

                // Flags
                val showHuntCompleteDialog = remember { mutableStateOf(false)}
                val completeHuntFlag = remember { mutableStateOf(tempCoinList.all { it.value == 0 }) }

                // List of total finds to display in panel
                // Will be filtered by coin type
                val listOfFinds = remember { mutableStateListOf<Find>() }

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.LightGray
                    )
                }
                Surface {

                    val showAlertDialog = remember { mutableStateOf(false)}
                    BackHandler {
                        showAlertDialog.value = true
                    }
                    if(showAlertDialog.value){
                        ConfirmCancelAlertDialog(
                            title = "Are you sure you want to stop this hunt?",
                            body = "This hunt and any finds will not be saved.",
                            confirmLabel = "Yes",
                            cancelLabel = "No",
                            toggledState = showAlertDialog,
                            onConfirm = {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            },
                            onCancel = { showAlertDialog.value = false }
                        )
                    }

                    Column {
                        TopAppBar(
                            backgroundColor = Color.White,
                            title = { Text(text = if (region == "US") "American Coin Hunt" else "Canadian Coin Hunt") },
                            elevation = 0.dp
                        )

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

                                arrangeCoinMap(coinList, region!!, keys, rolls)

                                val firstKey = keys[
                                    ArrayTools.firstIndexWhereNot(rolls, -1)!!
                                ]
                                val lastKey = keys[
                                    ArrayTools.lastIndexWhereNot(rolls, -1)!!
                                ]

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
                                            text = MoneyStringToSymbolUtil.convert(keys[index]),
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
                                listOfFinds = listOfFinds,
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
                                title = "Complete Hunt?",
                                body = "Make sure to double check your finds before wrapping up!",
                                confirmLabel = "Complete Hunt",
                                cancelLabel = "Cancel",
                                toggledState = showHuntCompleteDialog,
                                onConfirm = {
                                    val intent = Intent(context, MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                },
                                onCancel = { showHuntCompleteDialog.value = false }
                            )
                        }
                        Row(Modifier.weight(0.1f)) {
                            FullButton(
                                onClick = {
                                    showHuntCompleteDialog.value = true
                                },
                                text = "Complete Hunt",
                                enabled = completeHuntFlag.value
                            )
                        }
                    }
                }
            }
        }
    }
}