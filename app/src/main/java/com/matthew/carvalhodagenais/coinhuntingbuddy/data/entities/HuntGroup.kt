package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.DateConverter
import java.util.*

/**
 * Group of hunts to create summaries
 */
@Entity(tableName = "hunt_group_table")
@TypeConverters(DateConverter::class)
data class HuntGroup(
    @ColumnInfo(name = "date_hunted") var dateHunted: Date,
    @ColumnInfo(name = "comments") var comments: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}