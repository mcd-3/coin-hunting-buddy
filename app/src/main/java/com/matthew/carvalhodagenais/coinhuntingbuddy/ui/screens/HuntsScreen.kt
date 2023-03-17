package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch

private const val HUNTS_INDEX = 0

@Composable
fun HuntsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // Values for the date filter
    val currentDateFilter = remember { mutableStateOf(viewModel.dateFilter) }

    // Filter component values
    val openFilterDialog = remember { mutableStateOf(false) }
    val filterActive = remember {
        mutableStateOf(
            currentDateFilter.value != DateFilter.UNSET
        )
    }

    val allHunts by viewModel.getAllHuntGroups(currentDateFilter.value).observeAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.hunts_screen),
                scaffoldState = scaffoldState,
                navController = navController,
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                navController.navigate(Screen.NewHunt.route)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = stringResource(id = R.string.add_hunt_cd),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
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
        Column {
            // This handles the filter composable and it's functionality
            Filter(
                filterActive = filterActive,
                openFilterDialog = openFilterDialog,
                currentDateFilter = currentDateFilter,
                coroutineScope = coroutineScope,
                viewModel = viewModel
            )

            Row {
                if (allHunts == null) {
                    // Loading...
                } else if (allHunts!!.isEmpty()) {
                    NoItemsWarning(
                        topText = stringResource(id = R.string.no_hunts_label),
                        bottomText = stringResource(id = R.string.click_plus_start_hunt_label)
                    )
                } else if (allHunts!!.isNotEmpty()) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        allHunts!!.forEachIndexed { index, it ->
                            HuntGroupListItem(
                                huntGroup = it,
                                viewModel = viewModel,
                                onClick = {
                                    viewModel.setCurrentHuntGroup(it)
                                    navController.navigate(Screen.Details.route)
                                }
                            )

                            if (index != allHunts!!.lastIndex) {
                                Divider(
                                    color = Color.LightGray,
                                    thickness = 1.dp,
                                    modifier = Modifier.padding(bottom = 2.dp, top = 2.dp, start = 2.dp, end = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
