package com.matthew.carvalhodagenais.coinhuntingbuddy.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup

@Composable
fun HuntGroupListItem(
    huntGroup: HuntGroup,

) {
    Row {
        Text(text = huntGroup.dateHunted.toString())
    }
    Row {
        Text(text = huntGroup.regionId.toString())
    }
}