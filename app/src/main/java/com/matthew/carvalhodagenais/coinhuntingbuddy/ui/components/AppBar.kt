package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.topAppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import kotlinx.coroutines.launch

@Composable
fun AppBar(
    title: String,
    scaffoldState: ScaffoldState,
    navController: NavController? = null,
    isPopable: Boolean = false,
    actions: @Composable (RowScope.() -> Unit) = {}
) {
    val coroutineScope = rememberCoroutineScope()

    Surface {
        TopAppBar(
            backgroundColor = MaterialTheme.colors.topAppBar,
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
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = stringResource(id = R.string.menu_icon_cd)
                        )
                    }
                } else if (navController != null && isPopable) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.arrow_back_cd)
                        )
                    }
                }
            },
            actions = actions
        )
    }
}