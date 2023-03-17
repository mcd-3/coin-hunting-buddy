package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import android.content.Context
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

class MoneyStringToSymbolUtil {
    companion object {
        /**
         * Converts a coin string to it's appropriate symbol
         *
         * @param str String - String to convert to money symbol
         * @return String - Converted string to coin symbol
         */
        fun convert(str: String, context: Context): String {
            return when (str.lowercase()) {
                context.getString(R.string.ca_1c_plural).lowercase()
                    -> context.getString(R.string.money_1c_symbol)
                context.getString(R.string.ca_5c_plural).lowercase()
                    -> context.getString(R.string.money_5c_symbol)
                context.getString(R.string.ca_10c_plural).lowercase()
                    -> context.getString(R.string.money_10c_symbol)
                context.getString(R.string.ca_25c_plural).lowercase()
                    -> context.getString(R.string.money_25c_symbol)
                context.getString(R.string.ca_loonie_plural).lowercase()
                    -> context.getString(R.string.money_1d_symbol)
                context.getString(R.string.ca_toonie_plural).lowercase()
                    -> context.getString(R.string.money_2d_symbol)
                context.getString(R.string.us_penny_plural).lowercase()
                    -> context.getString(R.string.money_1c_symbol)
                context.getString(R.string.us_nickel_plural).lowercase()
                    -> context.getString(R.string.money_5c_symbol)
                context.getString(R.string.us_dime_plural).lowercase()
                    -> context.getString(R.string.money_10c_symbol)
                context.getString(R.string.us_quarter_plural).lowercase()
                    -> context.getString(R.string.money_25c_symbol)
                context.getString(R.string.us_hd_plural).lowercase()
                    -> context.getString(R.string.money_50c_symbol)
                context.getString(R.string.us_dollar_plural).lowercase()
                    -> context.getString(R.string.money_1d_symbol)
                else -> context.getString(R.string.not_found_label)
            }
        }

        fun singleToPlural(str: String, context: Context): String {
            return when (str.lowercase()) {
                "1 cent" -> context.getString(R.string.ca_1c_plural)
                "1 dollar (\"loonie\")" -> context.getString(R.string.ca_loonie_plural)
                "2 dollars (\"toonie\")" -> context.getString(R.string.ca_toonie_plural)
                "penny" -> context.getString(R.string.us_penny_plural)
                "nickel" -> context.getString(R.string.us_nickel_plural)
                "dime" -> context.getString(R.string.us_dime_plural)
                "quarter" -> context.getString(R.string.us_quarter_plural)
                "half-dollar" -> context.getString(R.string.us_hd_plural)
                "dollar" -> context.getString(R.string.us_dollar_plural)
                else -> str
            }
        }
    }
}