package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*

/**
 * Region of where a coin is from.
 *
 * Ex: Name: Canada, Code: CA
 */
@Entity(tableName = "region_table")
data class Region(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "code") var code: String,
    @ColumnInfo(name = "name") var name: String,
) {
    // Cached IDs for quick access
    companion object {
        const val REGION_CANADA_ID = 1
        const val REGION_USA_ID = 2
    }
}