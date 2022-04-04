package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@Composable
fun AppBar(title: String, scaffoldState: ScaffoldState) {

    val coroutineScope = rememberCoroutineScope()

    Surface {
        TopAppBar(
            modifier = Modifier.background(Color.Blue),
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menu Icon")
                }
            },
        )
    }
}