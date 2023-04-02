package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HalfBoldLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.LabelCard
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.secondaryText
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

private const val HUNTS_INDEX = 0

@Composable
fun FindDetailsScreen(
    navController: NavController,
    viewModel: MainActivityViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val find = viewModel.getCurrentFind()

    val hblFontSize = 14

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.find_details_screen),
                scaffoldState = scaffoldState,
                navController = navController,
                isPopable = true,
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: Go to EditFindDetails screen!
                        }
                    ) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = stringResource(id = R.string.edit_icon_cd),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = HUNTS_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Column {
            LabelCard(
                label = stringResource(id = R.string.overview_label),
                icon = Icons.Filled.Menu
            ) {
                Column {
                    HalfBoldLabel(
                        first = stringResource(id = R.string.date_hunted_half_label),
                        second = "Date goes here",
                        fontSize = hblFontSize,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 8.dp
                        )
                    )
                    HalfBoldLabel(
                        first = stringResource(id = R.string.coin_type_half_label),
                        second = "Coin Type goes here",
                        fontSize = hblFontSize,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 12.dp
                        )
                    )
                }
            }

            LabelCard(
                label = stringResource(id = R.string.coin_label),
                icon = Icons.Filled.Paid
            ) {
                val strings = FindStringGenerator.generate(
                    context = LocalContext.current,
                    year = find?.year,
                    mintMark = find?.mintMark,
                    variety = find?.variety,
                    error = find?.error,
                )
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(
                                    start = 24.dp,
                                    top = 2.dp,
                                    bottom = 2.dp
                                )
                        ) {
                            Text(
                                text = strings[0],
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(end = 24.dp)
                        ) {
                            Text(
                                text = "GRADE",
                                textAlign = TextAlign.End,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, top = 2.dp, bottom = 2.dp)
                        ) {
                            if (find?.variety.isNullOrEmpty()) {
                                Text(
                                    text = FindStringGenerator.getVarietyString(LocalContext.current, find?.variety),
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colors.secondaryText
                                )
                            } else {
                                Text(FindStringGenerator.getVarietyString(LocalContext.current, find?.variety))
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 24.dp, top = 2.dp, bottom = 12.dp)
                        ) {
                            if (find?.error.isNullOrEmpty()) {
                                Text(
                                    text = FindStringGenerator.getErrorString(LocalContext.current, find?.error),
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colors.secondaryText
                                )
                            } else {
                                Text(FindStringGenerator.getErrorString(LocalContext.current, find?.error))
                            }
                        }
                    }
                }
            }
        }
    }
}