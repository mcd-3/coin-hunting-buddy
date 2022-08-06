package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import androidx.compose.ui.text.toLowerCase

class MoneyStringToSymbolUtil {
    companion object {
        fun convert(str: String): String {
            return when (str.lowercase()) {
                "1 cents" -> "1¢"
                "5 cents" -> "5¢"
                "10 cents" -> "10¢"
                "25 cents" -> "25¢"
                "loonies" -> "$1"
                "toonies" -> "$2"
                "pennies" -> "1¢"
                "nickels" -> "5¢"
                "dimes" -> "10¢"
                "quarters" -> "25¢"
                "half-dollars" -> "50¢"
                "dollars" -> "$1"
                else -> "NA"
            }
        }
    }
}