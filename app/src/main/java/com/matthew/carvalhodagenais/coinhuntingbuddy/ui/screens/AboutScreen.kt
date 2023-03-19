package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.Hyperlink
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.secondaryText


private const val ABOUT_INDEX = 2

@Composable
fun AboutScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.about_screen),
                scaffoldState = scaffoldState
            )
        },
        drawerContent = {
            NavDrawer(
                scaffoldState = scaffoldState,
                navController = navController,
                selectedIndex = ABOUT_INDEX
            )
        },
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(id = R.string.app_name), fontSize = 20.sp)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.ic_coin_200),
                            contentDescription = "",
                            contentScale = ContentScale.Fit
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.author_label,
                                stringResource(id = R.string.author)
                            ),
                            fontSize = 18.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                stringResource(id = R.string.mit_label),
                                textAlign = TextAlign.Center
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Hyperlink(
                                text = stringResource(id = R.string.github_license_label),
                                tag = "license",
                                link = stringResource(id = R.string.github_license_link),
                                style = TextStyle(
                                    fontSize = 16.sp,
                                )
                            )
                        }
                    }

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
                                    .padding(bottom = 12.dp, end = 14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}