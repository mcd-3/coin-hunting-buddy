package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.FindStringGenerator

@Composable
fun SummaryFinds(
    label: String,
    rolls: Int?,
    listOfFinds: MutableList<Find>
) {
    Column {
        Row {
            Text(text = label)
        }
        Row {
            // [Rolls: X    |    Finds: X]
            Row {
                Text(text = "Rolls: $rolls")
            }
            Row {
                Text(text = "|")
            }
            Row {
                Text(text = "Finds: ${listOfFinds.size}")
            }
        }

        if (listOfFinds.isNotEmpty()) {
            listOfFinds.forEach {
                val findStringArray = FindStringGenerator.generate(
                    it.year,
                    it.mintMark,
                    it.variety,
                    it.error
                )
                // For each find, list each find via bullet point
                Row {
                    Column {
                        // Bullet point
                    }
                    Column {
                        Text(text = findStringArray[0])
                        Text(text = findStringArray[1])
                    }
                }
            }
        } else {
            Text(text = "No Finds :(")
        }
    }
}