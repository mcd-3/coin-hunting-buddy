package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

class FindStringGenerator {
    companion object {

        /**
         *
         * @return {Array} array of two strings
         */
        fun generate(
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
                coinStringFirst = "Unknown Coin"
                coinStringSecond = "No further details"
            } else {
                val yearStr = if (year === null) "Illegible Year" else year.toString()
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
                    "No major varieties or errors"
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