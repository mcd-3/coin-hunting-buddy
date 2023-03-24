package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Region

@Dao
interface RegionDAO {
    @Insert
    fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)

    @Query("SELECT * FROM region_table WHERE code = :code")
    suspend fun getRegionByCodeAsync(code: String): Region

    @Query("SELECT * FROM region_table WHERE code = :code")
    fun getRegionByCode(code: String): Region

    @Query("SELECT * FROM region_table WHERE id = :id")
    fun getRegionById(id: Int): LiveData<Region>

    @Query("SELECT name FROM region_table WHERE id = :id")
    fun getRegionNameById(id: Int): String

    @Query("SELECT * FROM region_table")
    fun getRegions(): LiveData<List<Region>>

    @Query("SELECT code FROM region_table")
    fun getRegionCodes(): LiveData<List<String>>
}