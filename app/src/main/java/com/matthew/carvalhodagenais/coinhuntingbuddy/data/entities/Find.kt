package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*

/**
 * Finds during a hunt
 */
@Entity(tableName = "finds_table",
    indices = [
        Index("coin_type_id"),
        Index("grade_id"),
        Index("hunt_id")],
    foreignKeys = [
        ForeignKey(
            entity = CoinType::class,
            parentColumns = ["id"],
            childColumns = ["coin_type_id"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = Grade::class,
            parentColumns = ["id"],
            childColumns = ["grade_id"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = Hunt::class,
            parentColumns = ["id"],
            childColumns = ["hunt_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class Find(
    @ColumnInfo(name = "variety") var variety: String? = null,
    @ColumnInfo(name = "year") var year: Short? = null,
    @ColumnInfo(name = "coin_type_id") var coinTypeId: Int,
    @ColumnInfo(name = "grade_id") var gradeId: Int? = null,
    @ColumnInfo(name = "hunt_id") var huntId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}