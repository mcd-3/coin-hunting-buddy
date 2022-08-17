package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.dataobjects.Find

@Composable
fun FindsPanel(
    findsList: List<Find>,
) {
    val startPadding = 8.dp
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = startPadding, end = 8.dp)
                    .background(Color.Gray)
            )

            Text(text = "Finds")

            Box(
                modifier = Modifier
                    .height(2.dp)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
                    .background(Color.Gray)
            )
        }

        if (findsList.isEmpty()) {
            Text(text = "None :(", modifier = Modifier.padding(start = startPadding))
        } else {
            findsList.forEach {
                Row {
                    Text(text = it.year, modifier = Modifier.padding(start = startPadding))
                    Text(text = it.variety, modifier = Modifier.padding(start = startPadding))
                }
            }
        }
    }
}