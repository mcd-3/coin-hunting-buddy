package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

enum class CoinTypes {
    PENNY,
    NICKEL,
    DIME,
    QUARTER,
    LOONIE_OR_HALF_DOLLAR,
    TOONIE_OR_US_DOLLAR,
    UNDEFINED
}

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

        fun stringToInt(str: String): CoinTypes {
            return when (str.lowercase()) {
                "1 cents" -> CoinTypes.PENNY
                "5 cents" -> CoinTypes.NICKEL
                "10 cents" -> CoinTypes.DIME
                "25 cents" -> CoinTypes.QUARTER
                "loonies" -> CoinTypes.LOONIE_OR_HALF_DOLLAR
                "toonies" -> CoinTypes.TOONIE_OR_US_DOLLAR
                "pennies" -> CoinTypes.PENNY
                "nickels" -> CoinTypes.NICKEL
                "dimes" -> CoinTypes.DIME
                "quarters" -> CoinTypes.QUARTER
                "half-dollars" -> CoinTypes.LOONIE_OR_HALF_DOLLAR
                "dollars" -> CoinTypes.TOONIE_OR_US_DOLLAR
                else -> CoinTypes.UNDEFINED
            }
        }
    }
}