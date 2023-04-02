package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

private const val FINDS_INDEX = 1

@Composable
fun FindsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // Values for the filter
    val currentDateFilter = remember { mutableStateOf(viewModel.findsDateFilter) }
    val currentCoinTypeFilter = remember { mutableStateOf(viewModel.coinTypeFilter) }

    // We need to pass these values here, otherwise the composable won't recompose
    val allFinds by viewModel.getAllFindsFiltered(
        currentDateFilter.value,
        currentCoinTypeFilter.value
    ).observeAsState()

    // Filter component values
    val openFilterDialog = remember { mutableStateOf(false) }
    val filterActive = remember {
        mutableStateOf(
            !(currentDateFilter.value == DateFilter.UNSET && currentCoinTypeFilter.value == null)
        )
    }

    // Back Handler to go back to the hunts screen
    BackHandler {
        navController.navigate("hunts_screen") {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.finds_screen),
                scaffoldState = scaffoldState
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = FINDS_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {

        Column {
            Filter(
                filterActive = filterActive,
                openFilterDialog = openFilterDialog,
                currentDateFilter = currentDateFilter,
                currentCoinTypeFilter = currentCoinTypeFilter,
                coroutineScope = coroutineScope,
                isFindsPage = true,
                viewModel = viewModel
            )

            Row {
                if (allFinds == null) {
                    // Loading
                } else if (allFinds!!.isEmpty()) {
                    NoItemsWarning(
                        topText = stringResource(id = R.string.no_finds_label),
                        bottomText = stringResource(id = R.string.start_hunt_add_find_label)
                    )
                } else if (allFinds!!.isNotEmpty()) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        allFinds!!.forEachIndexed { index, find ->
                            FindListItem(
                                find = find,
                                viewModel = viewModel,
                                onClick = {
                                    viewModel.setCurrentFind(find)
                                    navController.navigate("find_details_screen")
                                }
                            )

                            if (index != allFinds!!.lastIndex) {
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(
                                            bottom = 2.dp,
                                            top = 2.dp,
                                            start = 2.dp,
                                            end = 2.dp
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}