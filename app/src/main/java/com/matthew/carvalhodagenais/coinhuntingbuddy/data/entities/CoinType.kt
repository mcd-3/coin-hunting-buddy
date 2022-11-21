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
) {
    companion object {
        const val CT_CANADA_PENNY = 1
        const val CT_CANADA_NICKEL = 2
        const val CT_CANADA_DIME = 3
        const val CT_CANADA_QUARTER = 4
        const val CT_CANADA_HD = 5
        const val CT_CANADA_DOLLAR_LARGE = 6
        const val CT_CANADA_DOLLAR_LOONIE = 7
        const val CT_CANADA_TOONIE = 8
        const val CT_USA_PENNY = 9
        const val CT_USA_NICKEL = 10
        const val CT_USA_DIME = 11
        const val CT_USA_QUARTER = 12
        const val CT_USA_HD = 13
        const val CT_USA_DOLLAR = 14
    }
}