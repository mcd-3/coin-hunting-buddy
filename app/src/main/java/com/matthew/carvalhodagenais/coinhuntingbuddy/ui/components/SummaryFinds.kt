package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator

@Composable
fun SummaryFinds(
    label: String,
    rolls: Int?,
    listOfFinds: MutableList<Find>
) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
    ) {
        Row {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        // [Rolls: X    |    Finds: X]
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp, bottom = 2.dp)
        ) {
            Text(
                text = "Rolls: $rolls",
                textAlign = TextAlign.Start,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = "|",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Text(
                text = "Finds: ${listOfFinds.size}",
                textAlign = TextAlign.End,
                fontStyle = FontStyle.Italic,
                fontSize = 13.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        if (listOfFinds.isNotEmpty()) {
            listOfFinds.forEachIndexed { index, it ->
                val findStringArray = FindStringGenerator.generate(
                    it.year,
                    it.mintMark,
                    it.variety,
                    it.error
                )

                val modifier: Modifier = if (index == listOfFinds.lastIndex) {
                    Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
                } else {
                    Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 2.dp
                    )
                }

                // For each find, list each find via bullet point
                Row(
                    modifier = modifier
                ) {
                    Column {
                        val bullet = "\u2022"
                        Text(text = bullet, textAlign = TextAlign.Center, fontSize = 20.sp)
                    }
                    Column {
                        Text(text = findStringArray[0])
                        Text(text = findStringArray[1])
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {
                Column {
                    val bullet = "\u2022"
                    Text(text = bullet)
                }
                Column {
                    Text(text = "No finds :(")
                }
            }
        }
    }
}