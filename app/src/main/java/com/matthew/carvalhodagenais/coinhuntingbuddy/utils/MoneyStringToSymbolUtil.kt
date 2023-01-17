package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

class MoneyStringToSymbolUtil {
    companion object {
        /**
         * Converts a coin string to it's appropriate symbol
         *
         * @param str String - String to convert to money symbol
         * @return String - Converted string to coin symbol
         */
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

        fun singleToPlural(str: String): String {
            return when (str.lowercase()) {
                "1 cent" -> "1 Cents"
                "1 dollar (\"loonie\")" -> "Loonies"
                "2 dollars (\"toonie\")" -> "Toonies"
                "penny" -> "Pennies"
                "nickel" -> "Nickels"
                "dime" -> "Dimes"
                "quarter" -> "Quarters"
                "half-dollar" -> "Half-Dollars"
                "dollar" -> "Dollars"
                else -> str
            }
        }
    }
}