package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.CSVWriter
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.*


private const val SETTINGS_INDEX = 3


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val extStoragePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    )

    val deleteWarningState = remember { mutableStateOf(false) }
    val isDeleting = remember { mutableStateOf(false) }
    val isDownloading = remember { mutableStateOf(false) }

    // Back Handler to go back to the hunts screen
    BackHandler {
        // Only allow this to happen if deletion is not happening!
        if (!isDeleting.value && !isDownloading.value) {
            navController.navigate("hunts_screen") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
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
            Column {
                SettingsButton(
                    topText = stringResource(id = R.string.export_header_btn),
                    bottomText = stringResource(id = R.string.export_text_btn),
                    onClick = {
                       extStoragePermissionState.launchMultiplePermissionRequest()
                        val result = extStoragePermissionState.permissions.all { it.hasPermission }

                        val csvWriter = CSVWriter(context)

                        if (result) {
                            MainScope().launch {
                                isDownloading.value = true
                                val data = viewModel.getFindsData()
                                val file = csvWriter.write(data).await()

                                if (file != null) {
                                    val hasWritten = csvWriter.sendToDownloads(file).await()

                                    if (hasWritten) {
                                        Toast.makeText(
                                            context,
                                            context.getString(
                                                R.string.file_downloaded_toast,
                                                csvWriter.getFilePath()
                                            ),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.error_export_toast),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.no_data_to_export_toast),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                isDownloading.value = false
                            }
                        }
                    },
                    optionalText = if (extStoragePermissionState.permissions.all { it.hasPermission }) {
                        null
                    } else {
                        stringResource(id = R.string.permissions_label)
                    },
                )

                SettingsButton(
                    topText = stringResource(id = R.string.delete_header_btn),
                    bottomText = stringResource(id = R.string.delete_text_btn),
                    onClick = {
                        deleteWarningState.value = true
                    },
                    isDanger = true
                )
            }
        }
    }

    // This will only show up once data is being deleted!
    LoadingDialog(loadingState = isDeleting)

    // This will only show up once an XLSX file is being generated!
    LoadingDialog(loadingState = isDownloading, loadingString = "Generating XLSX file...")
    
    if (deleteWarningState.value) {
        ConfirmCancelAlertDialog(
            title = stringResource(id = R.string.delete_data_prompt_title),
            body = stringResource(id = R.string.delete_data_prompt_label),
            confirmLabel = stringResource(id = R.string.delete_prompt),
            cancelLabel = stringResource(id = R.string.cancel_prompt),
            toggledState = deleteWarningState,
            onConfirm = {
                deleteWarningState.value = false

                // Perform the deletion on another thread
                MainScope().launch {
                    isDeleting.value = true
                    val bool = viewModel.deleteData().await()

                    if (bool) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.data_deleted_toast),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_toast),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    isDeleting.value = false
                }
            },
            onCancel = { deleteWarningState.value = false }
        )
    }
}