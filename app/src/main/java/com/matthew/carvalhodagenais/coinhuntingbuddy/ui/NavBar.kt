package com.matthew.carvalhodagenais.coinhuntingbuddy.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NavBar(title: String) {
    Row {
        Text(text = title)
    }
}