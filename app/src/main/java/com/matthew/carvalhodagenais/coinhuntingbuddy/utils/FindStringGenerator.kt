package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import android.content.Context
import com.matthew.carvalhodagenais.coinhuntingbuddy.R

class FindStringGenerator {
    companion object {

        /**
         *
         * @return {Array} array of two strings
         */
        fun generate(
            context: Context,
            year: Short?,
            mintMark: String?,
            variety: String?,
            error: String?,
        ): Array<String> {
            val coinStringFirst: String
            val coinStringSecond: String

            if (
                year === null &&
                mintMark.isNullOrEmpty() &&
                variety.isNullOrEmpty() &&
                error.isNullOrEmpty()
            ) {
                coinStringFirst = context.getString(R.string.unknown_coin_label)
                coinStringSecond = context.getString(R.string.no_details_label)
            } else {
                val yearStr = if (year === null) context.getString(R.string.illegible_year_label) else year.toString()
                val mintMarkStr = if (mintMark.isNullOrEmpty()) "" else mintMark
                val varietyStr = if (variety.isNullOrEmpty()) "" else variety
                val errorStr = if (error.isNullOrEmpty()) "" else error

                coinStringFirst = if (year === null && mintMarkStr.isEmpty()) {
                    yearStr
                } else if (year === null) {
                    "$yearStr - $mintMarkStr"
                } else {
                    "$yearStr$mintMarkStr"
                }

                coinStringSecond = if (varietyStr.isEmpty() && errorStr.isEmpty()) {
                    context.getString(R.string.no_varieties_or_errors_label)
                } else if (varietyStr.isEmpty()) {
                    errorStr
                } else if (errorStr.isEmpty()) {
                    varietyStr
                } else {
                    "$varietyStr - $errorStr"
                }
            }

            return arrayOf(coinStringFirst, coinStringSecond)
        }
    }
}