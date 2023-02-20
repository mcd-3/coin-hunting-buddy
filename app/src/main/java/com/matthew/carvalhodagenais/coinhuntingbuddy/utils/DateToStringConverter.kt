package com.matthew.carvalhodagenais.coinhuntingbuddy.utils

import java.util.*

class DateToStringConverter {
    companion object {
        private fun pad(int: Int): String {
            if (int.toString().length < 2) {
                return "0$int"
            }
            return int.toString()
        }

        fun getString(date: Date): String {
            val cal = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"))
            cal.time = date
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH]
            val day = cal[Calendar.DAY_OF_MONTH]
            return "$year/${pad(month)}/${pad(day)}"
        }
    }
}