package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.TextNumberConverter

private const val HUNTS_INDEX = 0

private fun isRolls(stateMap: Map<String, MutableState<TextFieldValue>>): Boolean {
    return stateMap.values.stream().allMatch{ item ->
        TextNumberConverter.textFieldStringToInt(item.value) == 0
    }
}

private fun getCorrectRegionState(
    selectedRegionState: MutableState<String>,
    canState: Map<String, MutableState<TextFieldValue>>,
    usaState: Map<String, MutableState<TextFieldValue>>
): Map<String, MutableState<TextFieldValue>> {
    return when (selectedRegionState.value) {
        "Canada" -> canState
        "U.S.A" -> usaState
        else -> canState
    }
}

@Composable
fun NewHuntScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val selectedRegionState = remember { mutableStateOf("Canada") }
    val canadaStateMap = mapOf(
        "1 Cents" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "5 Cents" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "10 Cents" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "25 Cents" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Loonies" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Toonies" to remember { mutableStateOf(TextFieldValue(text = "0")) },
    )
    val usaStateMap = mapOf(
        "Pennies" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Nickels" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Dimes" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Quarters" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Half-Dollars" to remember { mutableStateOf(TextFieldValue(text = "0")) },
        "Dollars" to remember { mutableStateOf(TextFieldValue(text = "0")) },
    )
    var buttonIsEnabled: Boolean = false

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "New Hunt",
            scaffoldState = scaffoldState,
            navController = navController,
            isPopable = true
        ) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            buttonIsEnabled = !isRolls(
                getCorrectRegionState(
                    selectedRegionState,
                    canadaStateMap,
                    usaStateMap
                )
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(0.9f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "What are you hunting?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))
                RegionCard(
                    selectedRegion = "Canada",
                    selectedRegionState = selectedRegionState
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))
                RollsCard(
                    stateMap = getCorrectRegionState(
                        selectedRegionState,
                        canadaStateMap,
                        usaStateMap
                    )
                )
            }
            Column(
                modifier = Modifier.weight(0.1f, false),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    enabled = buttonIsEnabled,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Text("Begin Hunt", fontSize = 24.sp)
                }
            }
        }
    }
}
