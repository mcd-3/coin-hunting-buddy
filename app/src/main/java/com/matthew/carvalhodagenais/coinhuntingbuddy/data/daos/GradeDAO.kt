package com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Region

@Dao
interface GradeDAO {
    @Insert
    fun insert(grade: Grade)

    @Update
    suspend fun update(grade: Grade)

    @Delete
    suspend fun delete(grade: Grade)

    @Query("SELECT * FROM grade_table WHERE code = :code")
    suspend fun getGradeByCodeAsync(code: String): Grade

    @Query("SELECT * FROM grade_table WHERE id = :id")
    fun getGradeById(id: Int): LiveData<Grade>

    @Query("SELECT * FROM grade_table")
    fun getGrades(): LiveData<List<Grade>>

    @Query("SELECT code FROM grade_table")
    fun getGradeCodes(): LiveData<List<String>>
}