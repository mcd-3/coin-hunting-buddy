package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.HuntActivity
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.TextNumberConverter
import java.io.Serializable

private const val HUNTS_INDEX = 0

private fun isRolls(stateMap: Map<String, MutableState<TextFieldValue>>): Boolean {
    return stateMap.values.stream().allMatch{ item ->
        TextNumberConverter.textFieldStringToInt(item.value) == 0
    }
}

private fun getCorrectRegionState(
    selectedRegionState: MutableState<String>,
    canState: Map<String, MutableState<TextFieldValue>>,
    usaState: Map<String, MutableState<TextFieldValue>>,
    context: Context
): Map<String, MutableState<TextFieldValue>> {
    return when (selectedRegionState.value) {
        context.getString(R.string.ca_region) -> canState
        context.getString(R.string.us_region) -> usaState
        else -> canState
    }
}

private fun startNewHuntActivity(
    context: Context,
    stateMap: Map<String, MutableState<TextFieldValue>>,
    region: String
) {
    val intent = Intent(context, HuntActivity::class.java)

    val coinMap = mutableMapOf<String, Int>()
    stateMap.forEach {
        coinMap.put(
            it.key,
            TextNumberConverter.textFieldStringToInt(it.value.value)!!
        )
    }

    intent.putExtra("COIN_LIST", coinMap as Serializable)
    intent.putExtra("COIN_REGION", region)
    context.startActivity(intent)
}

@Composable
fun NewHuntScreen(navController: NavController) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val selectedRegionState = remember { mutableStateOf(context.getString(R.string.ca_region)) }
    val canadaStateMap = mapOf(
        stringResource(id = R.string.ca_1c_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.ca_5c_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.ca_10c_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.ca_25c_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.ca_loonie_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.ca_toonie_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
    )
    val usaStateMap = mapOf(
        stringResource(id = R.string.us_penny_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.us_nickel_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.us_dime_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.us_quarter_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.us_hd_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
        stringResource(id = R.string.us_dollar_plural) to remember { mutableStateOf(TextFieldValue(text = "0")) },
    )
    var buttonIsEnabled: Boolean = false

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.new_hunt_screen),
                scaffoldState = scaffoldState,
                navController = navController,
                isPopable = true
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = HUNTS_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            buttonIsEnabled = !isRolls(
                getCorrectRegionState(
                    selectedRegionState,
                    canadaStateMap,
                    usaStateMap,
                    context
                )
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(0.9f)
            ) {
                RegionCard(
                    selectedRegionState = selectedRegionState
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp))
                RollsCard(
                    stateMap = getCorrectRegionState(
                        selectedRegionState,
                        canadaStateMap,
                        usaStateMap,
                        context
                    )
                )
            }
            Column(
                modifier = Modifier.weight(0.1f, false),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FullButton(
                    onClick = {
                        startNewHuntActivity(
                            context,
                            // TODO: Replace these string comparisons with something more optimized
                            if (selectedRegionState.value === context.getString(R.string.us_region)) {
                                usaStateMap
                            } else {
                                canadaStateMap
                            },
                            if (selectedRegionState.value === context.getString(R.string.us_region)) {
                                context.getString(R.string.us_region_code)
                            } else {
                                context.getString(R.string.ca_region_code)
                            }
                        )
                    },
                    text = stringResource(id = R.string.begin_hunt_btn),
                    enabled = buttonIsEnabled
                )
            }
        }
    }
}
