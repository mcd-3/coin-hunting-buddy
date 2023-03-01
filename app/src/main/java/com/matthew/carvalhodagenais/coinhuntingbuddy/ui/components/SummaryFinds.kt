package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.secondaryText
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator

@Composable
fun SummaryFinds(
    label: String,
    rolls: Int?,
    listOfFinds: MutableList<Find>
) {
    // Bullet used in the bullet point list
    val bullet = "\u2022"
    val bulletPointModifier = Modifier.fillMaxHeight().padding(start = 2.dp, end = 8.dp)

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
        Row(modifier = Modifier.padding(bottom = 2.dp)) {
            Text(
                text = "Rolls: $rolls",
                textAlign = TextAlign.Start,
                fontSize = 13.sp,
                color = MaterialTheme.colors.secondaryText,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Row(modifier = Modifier
                .weight(1f)
            ) { }
        }

        if (listOfFinds.isNotEmpty()) {
            listOfFinds.forEachIndexed { index, it ->
                val findStringArray = FindStringGenerator.generate(
                    it.year,
                    it.mintMark,
                    it.variety,
                    it.error
                )

                val modifier: Modifier = if (index == listOfFinds.lastIndex || (index + 1) == listOfFinds.size ) {
                    Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 10.dp
                    )
                } else {
                    Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 4.dp
                    )
                }

                // For each find, list each find via bullet point
                Row(
                    modifier = modifier
                ) {
                    Column(modifier = Modifier.fillMaxHeight().padding(end = 4.dp)) {
                        Text(text = bullet, fontSize = 18.sp)
                    }
                    Column {
                        Text(text = findStringArray[0], fontSize = 16.sp, modifier = bulletPointModifier)

                        if (findStringArray[1] == "No major varieties or errors") {
                            Text(
                                text = findStringArray[1],
                                fontSize = 13.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color.Gray,
                                modifier = bulletPointModifier
                            )
                        } else {
                            Text(
                                text = findStringArray[1],
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = bulletPointModifier
                            )
                        }
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
            ) {
                Column {
                    Text(text = bullet, fontSize = 18.sp)
                }
                Column {
                    Text(
                        text = "No finds",
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = bulletPointModifier
                    )
                }
            }
        }
    }
}