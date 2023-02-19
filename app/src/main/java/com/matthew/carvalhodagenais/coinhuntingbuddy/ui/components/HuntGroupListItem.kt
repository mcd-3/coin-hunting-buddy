package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                huntTypeString.value = "Multiple"
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
            .padding(start = itemPadding, end = itemPadding)
            .clickable { onClick() }
    ) {
        Row {
            Text(text = DateToStringConverter.getString(huntGroup.dateHunted))
        }
        Row {
            Column {
                Text(text = huntTypeString.value)
            }
            Column {
                Text(text = huntGroup.regionId.toString())
            }
        }
    }
}