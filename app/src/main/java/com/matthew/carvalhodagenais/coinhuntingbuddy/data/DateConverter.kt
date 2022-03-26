package com.matthew.carvalhodagenais.coinhuntingbuddy.data

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return if (dateLong == null) null else Date(dateLong)
    }

    @TypeConverter
    fun fromLong(date: Date?): Long? {
        return date?.time
    }
}