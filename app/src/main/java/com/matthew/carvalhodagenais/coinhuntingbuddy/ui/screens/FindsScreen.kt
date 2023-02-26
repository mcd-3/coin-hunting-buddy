package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NavDrawer
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.AppBar
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.NoItemsWarning
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel

private const val FINDS_INDEX = 1

@Composable
fun FindsScreen(
    viewModel: MainActivityViewModel,
    navController: NavController
) {
    val scaffoldState = rememberScaffoldState()
    val allFinds by viewModel.getAllFinds().observeAsState()

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
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                allFinds!!.forEachIndexed { index, find ->
                    val strArray = FindStringGenerator.generate(
                        year = find.year,
                        mintMark = find.mintMark,
                        error = find.error,
                        variety = find.variety,
                    )

                    Column {
                        Row {
                            Text(text = strArray[0])
                        }
                        Row {
                            Text(text = strArray[1])
                        }
                    }

                    if (index != allFinds!!.lastIndex) {
                        Divider(
                            color = Color.LightGray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 2.dp, top = 2.dp, start = 2.dp, end = 2.dp)
                        )
                    }
                }
            }
        }
    }
}