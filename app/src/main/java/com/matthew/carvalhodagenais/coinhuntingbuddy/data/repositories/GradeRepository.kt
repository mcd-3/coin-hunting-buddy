package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.GradeDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade

class GradeRepository(application: Application) {

    private var gradeDAO: GradeDAO
    private var allGrades: LiveData<List<Grade>>
    private var allGradeCodes: LiveData<List<String>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        gradeDAO = database.gradeDao()
        allGrades = gradeDAO.getGrades()
        allGradeCodes = gradeDAO.getGradeCodes()
    }

    fun getGrades(): LiveData<List<Grade>> {
        return allGrades
    }

    fun getGradeCodes(): LiveData<List<String>> {
        return allGradeCodes
    }
}