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

    @Query("DELETE FROM finds_table WHERE hunt_id = :id")
    suspend fun deleteByHuntId(id: Int)

    @Query("SELECT * FROM finds_table ORDER BY hunt_id DESC")
    fun getAllFinds(): LiveData<List<Find>>

    @Query("SELECT * FROM finds_table ORDER BY hunt_id ASC")
    fun getAllFindsOlder(): LiveData<List<Find>>

    @Query("SELECT * FROM finds_table WHERE id = :id")
    fun getFindById(id: Int): LiveData<Find>

    @Query("SELECT * FROM finds_table WHERE hunt_id IN (SELECT id FROM hunt_table WHERE id = :id)")
    fun getFindByHuntId(id: Int): LiveData<List<Find>>

    @Query("SELECT * FROM finds_table WHERE hunt_id IN (SELECT id FROM hunt_table WHERE id = :id)")
    fun getFindByHuntIdSync(id: Int): List<Find>

    @Query("SELECT * FROM finds_table WHERE coin_type_id = :ctId ORDER BY id ASC")
    fun getFindsByCoinTypeId(ctId: Int): LiveData<List<Find>>

    @Query("SELECT * FROM finds_table WHERE coin_type_id = :ctId ORDER BY id DESC")
    fun getFindsByCoinTypeIdNewest(ctId: Int): LiveData<List<Find>>
}