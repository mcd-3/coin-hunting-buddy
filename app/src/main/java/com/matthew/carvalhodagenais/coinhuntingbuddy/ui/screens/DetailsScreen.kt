package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
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

    val fontSize = 14
    val halfLabelModifier = Modifier.padding(
        start = 20.dp,
        end = 20.dp,
        bottom = 4.dp
    )

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

            Column {
                Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 10.dp
                            )
                            .border(1.dp, Color(0xFFCECECE)),
                        elevation = 10.dp
                    ) {
                        Column {
                            Row {
                                FormLabel(text = "Overview", icon = Icons.Filled.Description)
                            }
                            Row {
                                Column {
                                    HalfBoldLabel(
                                        first = "Date Hunted: ",
                                        second = DateToStringConverter.getString(currentHuntGroup!!.dateHunted),
                                        fontSize = fontSize,
                                        modifier = halfLabelModifier
                                    )
                                    HalfBoldLabel(
                                        first = "Coin Region: ",
                                        second = if (currentHuntGroup.regionId == 1) "Canada" else "USA",
                                        fontSize = fontSize,
                                        modifier = halfLabelModifier
                                    )
                                }
                            }
                        }
                    }
                }

                val hunts = viewModel
                    .getHuntsByHuntGroup(huntGroup = currentHuntGroup!!)
                    .observeAsState()

                hunts.value?.forEach { hunt ->

                    val coinTypeName = viewModel
                        .getCoinTypeNameById(hunt.coinTypeId)
                        .observeAsState(initial = "")

                    val finds = viewModel
                        .getFindsByHunt(hunt)
                        .observeAsState()

                    Column {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    bottom = 10.dp
                                )
                                .border(1.dp, Color(0xFFCECECE)),
                            elevation = 10.dp
                        ) {
                            Column {
                                Row {
                                    FormLabel(text = coinTypeName.value, icon = Icons.Filled.Paid)
                                }
                                Row {
                                    HalfBoldLabel(
                                        first = "Rolls Searched: ",
                                        second = hunt.numberOfRolls.toString(),
                                        fontSize = fontSize,
                                        modifier = halfLabelModifier
                                    )
                                }
                                Row {
                                    finds.value?.forEach { find ->
                                        val grade = viewModel
                                            .getGradeById(find.gradeId!!)
                                            .observeAsState()

                                        if (grade.value != null) {

                                            val strArr = FindStringGenerator.generate(
                                                year = find.year,
                                                mintMark = find.mintMark,
                                                error = find.error,
                                                variety = find.variety
                                            )

                                            Column {
                                                Text(text = grade.value!!.code)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

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