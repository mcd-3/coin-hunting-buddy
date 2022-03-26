package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup

@Dao
interface HuntGroupDAO {
    @Insert
    suspend fun insert(huntGroup: HuntGroup)

    @Update
    suspend fun update(huntGroup: HuntGroup)

    @Delete
    suspend fun delete(huntGroup: HuntGroup)

    @Query("SELECT * FROM hunt_group_table WHERE id = :id")
    fun getHuntGroupById(id: Int): LiveData<HuntGroup>

    @Query("SELECT * FROM hunt_group_table")
    fun getHuntGroups(): LiveData<List<HuntGroup>>

    @Query("SELECT * FROM hunt_group_table ORDER BY date_hunted DESC")
    fun getHuntGroupsOrderedRecent(): LiveData<List<HuntGroup>>

    @Query("SELECT * FROM hunt_group_table ORDER BY date_hunted ASC")
    fun getHuntGroupsOrderedOlder(): LiveData<List<HuntGroup>>
}