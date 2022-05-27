package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.Screen
import kotlinx.coroutines.launch

@Composable
fun AppBar(
    title: String,
    scaffoldState: ScaffoldState,
    navController: NavController? = null,
    isPopable: Boolean = false,
    hasAddButton: Boolean = false
) {
    val coroutineScope = rememberCoroutineScope()

    Surface {
        TopAppBar(
            backgroundColor = Color.White,
            title = { Text(text = title) },
            navigationIcon = {
                if (!isPopable) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                    }
                } else if (navController != null && isPopable) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Go back!")
                    }
                }
            },
            actions = {
                if (navController != null && hasAddButton) {
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
            }
        )
    }
}