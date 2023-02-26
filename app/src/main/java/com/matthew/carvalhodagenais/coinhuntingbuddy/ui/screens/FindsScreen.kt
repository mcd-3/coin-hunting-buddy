package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

private const val FINDS_INDEX = 1

@Composable
fun FindsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val allFinds by viewModel.getAllFinds().observeAsState()

    // Values for the date filter
    val currentDateFilter = remember { mutableStateOf(viewModel.dateFilter) }

    // Filter component values
    val openFilterDialog = remember { mutableStateOf(false) }
    val filterActive = remember {
        mutableStateOf(
            currentDateFilter.value != DateFilter.UNSET
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(title = "Finds", scaffoldState = scaffoldState) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = FINDS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {

        if (allFinds == null) {
            // Loading
        } else if (allFinds!!.isEmpty()) {
            NoItemsWarning(topText = "No finds", bottomText = "Start a hunt to add some!")
        } else if (allFinds!!.isNotEmpty()) {
            Column {
                // This handles the filter composable and it's functionality
                Filter(
                    filterActive = filterActive,
                    openFilterDialog = openFilterDialog,
                    currentDateFilter = currentDateFilter,
                    coroutineScope = coroutineScope,
                    viewModel = viewModel
                )
                
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    allFinds!!.forEachIndexed { index, find ->
                        val strArray = FindStringGenerator.generate(
                            year = find.year,
                            mintMark = find.mintMark,
                            error = find.error,
                            variety = find.variety,
                        )

                        Column(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                            val rowPadding = 16.dp
                            Row(modifier = Modifier.padding(start = rowPadding)) {
                                val grade = viewModel
                                    .getGradeById(find.gradeId!!)
                                    .observeAsState()

                                Text(text = "${strArray[0]} - ${grade.value?.code}")
                            }
                            Row(modifier = Modifier.padding(start = rowPadding, bottom = 8.dp)) {
                                if (strArray[1] == "No major varieties or errors") {
                                    Text(
                                        text = strArray[1],
                                        fontStyle = FontStyle.Italic,
                                        fontSize = 14.sp
                                    )
                                } else {
                                    Text(text = strArray[1], fontSize = 14.sp)
                                }
                            }
                            Row(modifier = Modifier.padding(start = rowPadding, end = rowPadding)) {
                                val dateHunted = viewModel
                                    .getDateHuntedForFind(find.huntId)
                                    .observeAsState()
                                val coinTypeName = viewModel
                                    .getCoinTypeNameById(find.coinTypeId)
                                    .observeAsState()

                                Column(modifier = Modifier.weight(1f)) {
                                    HalfBoldLabel(
                                        first = "Date Hunted: ",
                                        second =
                                        if (dateHunted.value == null) ""
                                        else DateToStringConverter.getString(dateHunted.value!!),
                                        fontSize = 16,
                                        modifier = Modifier
                                    )
                                }
                                Column(
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text =
                                        if (coinTypeName.value == null) ""
                                        else coinTypeName.value!!,
                                        textAlign = TextAlign.End,
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }

                        if (index != allFinds!!.lastIndex) {
                            Divider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .padding(
                                        bottom = 2.dp,
                                        top = 2.dp,
                                        start = 2.dp,
                                        end = 2.dp
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}