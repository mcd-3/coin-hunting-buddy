package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.ConfirmCancelAlertDialog
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch

private const val HUNTS_INDEX = 0

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: MainActivityViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val currentHuntGroup = viewModel.getCurrentHuntGroup()
    val coroutineScope = rememberCoroutineScope()
    val openDialog = remember { mutableStateOf(false)  }
    val context = LocalContext.current

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "Details",
            scaffoldState = scaffoldState,
            navController = navController,
            isPopable = true,
            actions = {
                IconButton(
                    onClick = {
                        // Open the warning dialog
                        openDialog.value = true
                    }
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete hunt", tint = Color.Red)
                }
            }
        ) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Box(modifier = Modifier
            .background(Color.White)
            .fillMaxSize()) {
            Text(text = currentHuntGroup.toString())}

        if (openDialog.value) {
            ConfirmCancelAlertDialog(
                title = "Are you sure you want to delete this hunt?",
                body = "This action cannot be undone",
                confirmLabel = "Delete",
                cancelLabel = "Cancel",
                toggledState = openDialog,
                onConfirm = {
                    coroutineScope.launch {
                        viewModel.deleteHunt()
                    }
                    openDialog.value = false
                    Toast.makeText(context, "Hunt Deleted", Toast.LENGTH_LONG).show()
                },
                onCancel = { openDialog.value = false }
            )
        }
    }
}