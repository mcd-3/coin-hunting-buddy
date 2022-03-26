package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*

/**
 * Individual hunts of specific coin types
 */
@Entity(tableName = "hunt_table",
    indices = [
        Index("coin_type_id"),
        Index("hunt_group_id")],
    foreignKeys = [
        ForeignKey(
            entity = CoinType::class,
            parentColumns = ["id"],
            childColumns = ["coin_type_id"],
            onDelete = ForeignKey.NO_ACTION),
        ForeignKey(
            entity = HuntGroup::class,
            parentColumns = ["id"],
            childColumns = ["hunt_group_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class Hunt(
    @ColumnInfo(name = "number_of_rolls") var numberOfRolls: Int,
    @ColumnInfo(name = "coin_type_id") var coinTypeId: Int,
    @ColumnInfo(name = "hunt_group_id") var huntGroupId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}