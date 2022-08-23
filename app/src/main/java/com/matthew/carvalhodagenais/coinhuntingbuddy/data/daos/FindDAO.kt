package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find

@Dao
interface FindDAO {
    @Insert
    suspend fun insert(find: Find)

    @Update
    suspend fun update(find: Find)

    @Delete
    suspend fun delete(find: Find)

    @Query("SELECT * FROM finds_table")
    fun getAllFinds(): LiveData<List<Find>>

    @Query("SELECT * FROM finds_table WHERE id = :id")
    fun getFindById(id: Int): LiveData<Find>

    @Query("SELECT * FROM finds_table WHERE hunt_id IN (SELECT * FROM hunt_table WHERE id = :id)")
    fun getFindByHuntId(id: Int): LiveData<List<Find>>
}