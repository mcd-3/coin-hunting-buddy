package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*

/**
 * Grade of a coin according to the Sheldon Grading Scale
 *
 * Ex: Name: Mint State, Code: MS
 */
@Entity(tableName = "grade_table")
data class Grade(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "code") var code: String,
    @ColumnInfo(name = "name") var name: String,
)