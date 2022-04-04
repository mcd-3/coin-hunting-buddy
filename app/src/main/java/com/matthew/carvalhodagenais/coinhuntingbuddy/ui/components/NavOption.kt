package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun NavOption(
    title: String,
    icon: ImageVector,
    scaffoldState: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val fontSize = 18.sp
    val paddingTopBottom = 16.dp
    val paddingStart = 24.dp
    val iconColumnWeight = 0.25f
    val textColumnWeight = 0.75f

    Row(modifier = Modifier.fillMaxWidth()
        .clickable(
            onClick = {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        )
    ) {
        Column(
            modifier = Modifier.weight(iconColumnWeight).padding(
                start = paddingStart,
                top = paddingTopBottom,
                bottom = paddingTopBottom
            ),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
             imageVector = icon,
             contentDescription = ""
            )
        }
        Column(
            modifier = Modifier.weight(textColumnWeight).padding(
                top = paddingTopBottom,
                bottom = paddingTopBottom
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = fontSize),
            )
        }
    }
}