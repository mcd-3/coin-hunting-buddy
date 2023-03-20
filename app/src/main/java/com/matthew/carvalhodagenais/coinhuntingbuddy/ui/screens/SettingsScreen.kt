package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.ConfirmCancelAlertDialog
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.SettingsButton

private const val SETTINGS_INDEX = 3

@Composable
fun SettingsScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val deleteWarningState = remember { mutableStateOf(false) }

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
                title = stringResource(id = R.string.settings_screen),
                scaffoldState = scaffoldState
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = SETTINGS_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column() {
                SettingsButton(
                    topText = "Export to CSV",
                    bottomText = "Export all finds to a CSV file",
                    onClick = {
                        Log.e("CLICKED", "Hello world!")
                    }
                )

                SettingsButton(
                    topText = "Delete Data",
                    bottomText = "Deletes all hunts and finds from the app",
                    onClick = {
                        deleteWarningState.value = true
                    },
                    isDanger = true
                )
            }
        }
    }
    
    if (deleteWarningState.value) {
        ConfirmCancelAlertDialog(
            title = "Delete Data",
            body = "Are you sure you want to delete all hunts and finds from the app?\n\nTHIS ACTION IS PERMANENT!!",
            confirmLabel = "DELETE",
            cancelLabel = "CANCEL",
            toggledState = deleteWarningState,
            onConfirm = {
                deleteWarningState.value = false
                Toast.makeText(
                    context,
                    "App Data Deleted",
                    Toast.LENGTH_LONG
                ).show()
            },
            onCancel = { deleteWarningState.value = false }
        )
    }
}