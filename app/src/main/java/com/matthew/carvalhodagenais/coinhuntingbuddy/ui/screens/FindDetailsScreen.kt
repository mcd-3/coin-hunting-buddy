package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.HalfBoldLabel
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.LabelCard
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
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
    val halfLabelModifier = Modifier.padding(
        start = 20.dp,
        end = 20.dp,
        bottom = 8.dp
    )

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
                        modifier = halfLabelModifier
                    )
                    HalfBoldLabel(
                        first = stringResource(id = R.string.coin_type_half_label),
                        second = "Coin Type goes here",
                        fontSize = hblFontSize,
                        modifier = halfLabelModifier
                    )
                }
            }

            LabelCard(
                label = stringResource(id = R.string.coin_label),
                icon = Icons.Filled.Paid
            ) {
                Text("Hello World")
            }
        }
    }
}