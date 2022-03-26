package com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities

import androidx.room.*

/**
 * Type of coin being hunted
 *
 * Ex: 1 penny, 5 cents, 2 dollars...
 */
@Entity(tableName = "coin_type_table",
    indices = [Index("region_id")],
    foreignKeys = [
        ForeignKey(
            entity = Region::class,
            parentColumns = ["id"],
            childColumns = ["region_id"],
            onDelete = ForeignKey.NO_ACTION)])
data class CoinType(
    @PrimaryKey(autoGenerate = false) var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "denomination") var denomination: Double,
    @ColumnInfo(name = "region_id") var regionId: Int
)