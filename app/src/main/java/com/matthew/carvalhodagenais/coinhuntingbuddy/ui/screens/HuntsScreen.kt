package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HuntGroupListItem
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NoItemsWarning
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch

private const val HUNTS_INDEX = 0

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HuntsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val allHunts by viewModel.allHuntGroups.observeAsState()

    // Values for the date filter
    val currentDateFilter = remember { mutableStateOf(DateFilter.UNSET) }
    var selectedDateFilterOption by remember { mutableStateOf(currentDateFilter.value) }

    // Filter component values
    val openFilterDialog = remember { mutableStateOf(false) }
    val filterActive = remember {
        mutableStateOf(
            currentDateFilter.value != DateFilter.UNSET
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "Hunts",
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
                    Icon(Icons.Filled.Add, contentDescription = "Add hunt")
                }
            }
        )},
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Column {
            // TODO: Turn this into a composable fun
            Row(modifier = Modifier.padding(bottom = 6.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    if (filterActive.value) {
                        Text(
                            text = "Filter active",
                            modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "No filter active",
                            modifier = Modifier.padding(start = 20.dp, top = 12.dp),
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                openFilterDialog.value = true
                            }
                        }
                    ) {
                        if (filterActive.value) {
                            Icon(Icons.Filled.FilterAlt, contentDescription = "Filter Active")
                        } else {
                            Icon(Icons.Outlined.FilterAlt, contentDescription = "Filter")
                        }
                    }
                }
            }
            Row {
                if (allHunts == null) {
                    // Loading...
                } else if (allHunts!!.isEmpty()) {
                    NoItemsWarning(topText = "No hunts", bottomText = "Click \"+\" to start one!")
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

        if (openFilterDialog.value) {
            AlertDialog(
                title = { Text(text = "Add/Remove Filter\n") },
                onDismissRequest = {
                    openFilterDialog.value = false
                    selectedDateFilterOption = currentDateFilter.value
                },
                confirmButton = {
                    TextButton(onClick = {
                        openFilterDialog.value = false
                        currentDateFilter.value = selectedDateFilterOption
                        filterActive.value = currentDateFilter.value != DateFilter.UNSET
                    }){
                        Text(text = "Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openFilterDialog.value = false
                        selectedDateFilterOption = currentDateFilter.value
                    }) {
                        Text(text = "Cancel")
                    }
                },
                text = {
                    Row {
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
                            },
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = 4.dp)
                                .align(Alignment.Bottom),
                        ) {
                            TextField(
                                readOnly = true,
                                value = TextFieldValue(selectedDateFilterOption.dateFilter),
                                onValueChange = { },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                label = { Text(text = "Date From") },
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                }
                            ) {
                                DateFilter.values().forEach {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedDateFilterOption = it
                                            expanded = false
                                        }
                                    ) {
                                        Text(text = it.dateFilter)
                                    }
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}
