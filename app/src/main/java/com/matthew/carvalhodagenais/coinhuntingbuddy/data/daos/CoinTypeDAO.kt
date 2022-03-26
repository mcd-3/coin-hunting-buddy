package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType

@Dao
interface CoinTypeDAO {
    @Insert
    fun insert(coinType: CoinType)

    @Update
    suspend fun update(coinType: CoinType)

    @Delete
    suspend fun delete(coinType: CoinType)

    @Query("SELECT * FROM coin_type_table WHERE region_id IN (SELECT code FROM region_table WHERE code = :code)")
    fun getCoinTypesByRegionCode(code: String): LiveData<List<CoinType>>

    @Query("SELECT * FROM coin_type_table WHERE id = :id")
    fun getCoinTypeById(id: Int): LiveData<CoinType>

    @Query("SELECT * FROM coin_type_table")
    fun getCoinTypes(): LiveData<List<CoinType>>
}