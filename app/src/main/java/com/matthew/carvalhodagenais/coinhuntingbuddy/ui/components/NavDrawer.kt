package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens.Screen
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.secondaryText

private const val HUNTS_INDEX = 0
private const val FINDS_INDEX = 1
private const val ABOUT_INDEX = 2
private const val SETTINGS_INDEX = 3

@Composable
fun NavDrawer(
    scaffoldState: ScaffoldState,
    navController: NavController,
    selectedIndex: Int
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.cardBackground),
    ) {
        // Header
        Column(modifier = Modifier.weight(1.5f)) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_coin_200),
                        contentDescription = stringResource(id = R.string.logo_icon_cd),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                ) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.crh_label),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Bottom)
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.buddy_label),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Top)
                        )
                    }
                }
            }
        }

        // Nav Options
        Column(
            modifier = Modifier.weight(12f),
            verticalArrangement = Arrangement.Center
        ) {
            Divider()
            NavOption(
                title = stringResource(id = R.string.hunts_nav_option),
                icon = Icons.Filled.AccountBalance,
                scaffoldState = scaffoldState,
                navController = navController,
                navRoute = Screen.Hunts.route,
                isSelected = (selectedIndex != HUNTS_INDEX)
            )
            Divider()
            NavOption(
                title = stringResource(id = R.string.finds_nav_option),
                icon = Icons.Filled.Toll,
                scaffoldState = scaffoldState,
                navController = navController,
                navRoute = Screen.Finds.route,
                isSelected = (selectedIndex != FINDS_INDEX)
            )
            Divider()
            NavOption(
                title = stringResource(id = R.string.about_nav_option),
                icon = Icons.Filled.Info,
                scaffoldState = scaffoldState,
                navController = navController,
                navRoute = Screen.About.route,
                isSelected = (selectedIndex != ABOUT_INDEX)
            )
            Divider()
            NavOption(
                title = stringResource(id = R.string.settings_nav_option),
                icon = Icons.Filled.Settings,
                scaffoldState = scaffoldState,
                navController = navController,
                navRoute = Screen.Settings.route,
                isSelected = (selectedIndex != SETTINGS_INDEX)
            )
            Divider()
        }

        // Version
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(
                        id = R.string.version_label,
                        stringResource(id = R.string.version)
                    ),
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colors.secondaryText,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 8.dp, end = 12.dp)
                )
            }
        }
    }
}