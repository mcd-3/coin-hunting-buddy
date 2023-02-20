package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun HuntGroupListItem(
    huntGroup: HuntGroup,
    viewModel: MainActivityViewModel,
    onClick: () -> Unit
) {
    val itemPadding = 20.dp
    val huntTypeString = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val list = viewModel.getHuntType(huntGroup).await()

            if (list.size > 1) {
                huntTypeString.value = "Multiple Coin Types"
            } else if (list.size == 1) {
                huntTypeString.value = list[0].name
            } else {
                huntTypeString.value = "Nothing found"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(top = 2.dp, bottom = 2.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = itemPadding,
                end = itemPadding,
                bottom = 2.dp
            )
        ) {
            Text(
                text = DateToStringConverter.getString(huntGroup.dateHunted),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = itemPadding,
                end = itemPadding
            )
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = huntTypeString.value)
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                val regionStr = when (huntGroup.regionId) {
                    1 -> "\uD83C\uDDE8\uD83C\uDDE6"
                    2 -> "\uD83C\uDDFA\uD83C\uDDF8"
                    else -> "❓"
                }
                Text(text = regionStr)
            }
        }
    }
}