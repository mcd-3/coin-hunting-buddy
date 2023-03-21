package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import java.util.*

@Dao
interface HuntGroupDAO {
    @Insert
    fun insert(huntGroup: HuntGroup): Long

    @Update
    suspend fun update(huntGroup: HuntGroup)

    @Delete
    suspend fun delete(huntGroup: HuntGroup)

    @Query("SELECT * FROM hunt_group_table WHERE id = :id")
    fun getHuntGroupById(id: Int): LiveData<HuntGroup>

    @Query("SELECT * FROM hunt_group_table")
    fun getHuntGroups(): LiveData<List<HuntGroup>>

    @Query("SELECT * FROM hunt_group_table")
    fun getHuntGroupsSync(): List<HuntGroup>

    @Query("SELECT * FROM hunt_group_table ORDER BY date_hunted DESC")
    fun getHuntGroupsOrderedRecent(): LiveData<List<HuntGroup>>

    @Query("SELECT * FROM hunt_group_table ORDER BY date_hunted ASC")
    fun getHuntGroupsOrderedOlder(): LiveData<List<HuntGroup>>

    @Query("SELECT date_hunted FROM hunt_group_table INNER JOIN hunt_table ON hunt_table.hunt_group_id = hunt_group_table.id WHERE hunt_table.id = :id")
    fun getDateHuntedByHuntId(id: Int): LiveData<Date>
}