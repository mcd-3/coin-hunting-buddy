package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components.*

private const val HUNTS_INDEX = 0

@Composable
fun NewHuntScreen(navController: NavController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBar(
            title = "New Hunt",
            scaffoldState = scaffoldState,
            navController = navController,
            isPopable = true
        ) },
        drawerContent = { NavDrawer(
            scaffoldState = scaffoldState,
            navController = navController,
            selectedIndex = HUNTS_INDEX
        )},
        drawerElevation = 12.dp,
        drawerScrimColor = Color.Black.copy(0.3f)
    ) {
        Column(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(0.9f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "What are you hunting?",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                    )
                }
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))
                RegionCard(selectedRegion = "Canada")
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))
                RollsCard()
            }
            Column(
                modifier = Modifier.weight(0.1f, false),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    enabled = false,
                    shape = RectangleShape,
                    modifier = Modifier.fillMaxHeight().fillMaxWidth()
                ) {
                    Text("Begin Hunt", fontSize = 24.sp)
                }
            }
        }
    }
}
