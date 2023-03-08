package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBorder
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.deleteIcon
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
        bottom = 8.dp
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
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete hunt",
                        tint = MaterialTheme.colors.deleteIcon
                    )
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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 10.dp
                            ),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
                        elevation = 4.dp,
                        backgroundColor = MaterialTheme.colors.cardBackground
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
                                ),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.cardBorder),
                            elevation = 4.dp,
                            backgroundColor = MaterialTheme.colors.cardBackground
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

                                finds.value?.forEachIndexed { index, find ->
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

                                        Row {
                                            Column(modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                                            ) {
                                                Text(text = "${strArr[0]} : ${grade.value!!.code}")

                                                if (strArr[1] == "No major varieties or errors") {
                                                    Text(
                                                        text = strArr[1],
                                                        fontSize = 13.sp,
                                                        fontStyle = FontStyle.Italic,
                                                        color = Color.Gray,
                                                    )
                                                } else {
                                                    Text(
                                                        text = strArr[1],
                                                        fontSize = 13.sp,
                                                        color = Color.Gray,
                                                    )
                                                }
                                            }
                                        }

                                        if (index != finds.value!!.size - 1) {
                                            Row {
                                                Divider(
                                                    color = Color(0xFFB6B3B3),
                                                    thickness = 2.dp,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 20.dp,
                                                            end = 20.dp,
                                                            bottom = 8.dp
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