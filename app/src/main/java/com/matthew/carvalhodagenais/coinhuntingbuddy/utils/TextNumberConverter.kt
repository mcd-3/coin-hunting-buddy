package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import androidx.compose.ui.text.input.TextFieldValue

class TextNumberConverter {
    companion object {
        fun textFieldStringToInt(tfv: TextFieldValue): Int? {
            return try {
                tfv.text.trim().toInt()
            } catch (e: Exception) {
                null
            }
        }
    }
}