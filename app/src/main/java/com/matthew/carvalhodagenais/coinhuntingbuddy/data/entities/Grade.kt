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
) {
    // Cached IDs for quick access
    companion object {
        const val GRADE_PO_ID = 1
        const val GRADE_FR_ID = 2
        const val GRADE_AG_ID = 3
        const val GRADE_G_ID = 4
        const val GRADE_F_ID = 5
        const val GRADE_VF_ID = 6
        const val GRADE_AU_ID = 7
        const val GRADE_MS_ID = 8
    }
}