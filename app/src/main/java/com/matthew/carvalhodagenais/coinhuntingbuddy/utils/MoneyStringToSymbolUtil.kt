package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

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

        fun stringToInt(str: String): Int {
            return when (str.lowercase()) {
                "1 cents" -> 0
                "5 cents" -> 1
                "10 cents" -> 2
                "25 cents" -> 3
                "loonies" -> 4
                "toonies" -> 5
                "pennies" -> 0
                "nickels" -> 1
                "dimes" -> 2
                "quarters" -> 3
                "half-dollars" -> 4
                "dollars" -> 5
                else -> -1
            }
        }
    }
}