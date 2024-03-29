package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.matthew.carvalhodagenais.coinhuntingbuddy.R
import com.matthew.carvalhodagenais.coinhuntingbuddy.ui.theme.cardBackground

@Composable
fun RollsCard(stateMap: Map<String, MutableState<TextFieldValue>>) {
    LabelCard(
        label = stringResource(id = R.string.number_of_rolls_label),
        icon = Icons.Filled.Calculate
    ) {
        stateMap.forEach{ item ->
            CounterRow(label = item.key, mutVal = item.value)

            if (item.key != stringResource(id = R.string.toonie_key) &&
                item.key != stringResource(id = R.string.dollar_key)
            ) {
                Divider(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 12.dp, start = 14.dp, end = 14.dp))
            } else {
                Divider(
                    modifier = Modifier.padding(top = 12.dp),
                    color = MaterialTheme.colors.cardBackground
                )
            }
        }
    }
}