package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import kotlinx.coroutines.*

@Composable
fun HuntGroupListItem(
    huntGroup: HuntGroup,
    viewModel: MainActivityViewModel,
    onClick: () -> Unit,
) {
    val itemPadding = 20.dp
    val huntTypeString = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit, key2 = huntGroup) {
        withContext(Dispatchers.IO) {
            val list = viewModel.getHuntType(huntGroup).await()

            if (list.size > 1) {
                huntTypeString.value = context.getString(R.string.multiple_coin_types_label)
            } else if (list.size == 1) {
                huntTypeString.value = list[0].name
            } else {
                huntTypeString.value = context.getString(R.string.not_found_label)
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
                    1 -> stringResource(id = R.string.flag_ca)
                    2 -> stringResource(id = R.string.flag_us)
                    else -> stringResource(id = R.string.question_mark)
                }
                Text(text = regionStr)
            }
        }
    }
}