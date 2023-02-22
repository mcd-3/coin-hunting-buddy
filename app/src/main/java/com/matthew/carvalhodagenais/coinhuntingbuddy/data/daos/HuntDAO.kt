package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Hunt
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup

@Dao
interface HuntDAO {
    @Insert
    fun insert(hunt: Hunt): Long

    @Update
    suspend fun update(hunt: Hunt)

    @Delete
    suspend fun delete(hunt: Hunt)

    @Query("SELECT * FROM hunt_table WHERE hunt_group_id = :id")
    fun getHuntsByHuntGroupId(id: Int): List<Hunt>

    @Query("SELECT * FROM hunt_table WHERE hunt_group_id = :id")
    fun getLiveHuntsByHuntGroupId(id: Int): LiveData<List<Hunt>>
}