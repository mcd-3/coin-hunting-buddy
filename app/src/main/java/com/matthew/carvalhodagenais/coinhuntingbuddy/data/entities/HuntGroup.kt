package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.converters.DateConverter
import java.util.*

/**
 * Group of hunts to create summaries
 */
@Entity(
    tableName = "hunt_group_table",
    indices = [
        Index("region_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["region_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
@TypeConverters(DateConverter::class)
data class HuntGroup(
    @ColumnInfo(name = "date_hunted") var dateHunted: Date,
    @ColumnInfo(name = "comments") var comments: String? = "",
    @ColumnInfo(name = "region_id") var regionId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}