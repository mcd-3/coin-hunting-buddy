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

    fun getGradeCodesCached(): List<String> {
        return listOf(
            Grade.GRADE_PO_CODE,
            Grade.GRADE_FR_CODE,
            Grade.GRADE_AG_CODE,
            Grade.GRADE_G_CODE,
            Grade.GRADE_F_CODE,
            Grade.GRADE_VF_CODE,
            Grade.GRADE_AU_CODE,
            Grade.GRADE_MS_CODE
        )
    }

    fun getGradeCodeByIdCached(id: Int?): String {
        return when(id) {
            Grade.GRADE_PO_ID -> Grade.GRADE_PO_CODE
            Grade.GRADE_FR_ID -> Grade.GRADE_FR_CODE
            Grade.GRADE_AG_ID ->Grade.GRADE_AG_CODE
            Grade.GRADE_G_ID -> Grade.GRADE_G_CODE
            Grade.GRADE_F_ID -> Grade.GRADE_F_CODE
            Grade.GRADE_VF_ID -> Grade.GRADE_VF_CODE
            Grade.GRADE_AU_ID -> Grade.GRADE_AU_CODE
            Grade.GRADE_MS_ID -> Grade.GRADE_MS_CODE
            else -> "NA"
        }
    }

    fun getGradeIdByCodeCached(code: String?): Int {
        return when(code) {
            Grade.GRADE_PO_CODE -> Grade.GRADE_PO_ID
            Grade.GRADE_FR_CODE -> Grade.GRADE_FR_ID
            Grade.GRADE_AG_CODE ->Grade.GRADE_AG_ID
            Grade.GRADE_G_CODE -> Grade.GRADE_G_ID
            Grade.GRADE_F_CODE -> Grade.GRADE_F_ID
            Grade.GRADE_VF_CODE -> Grade.GRADE_VF_ID
            Grade.GRADE_AU_CODE -> Grade.GRADE_AU_ID
            Grade.GRADE_MS_CODE -> Grade.GRADE_MS_ID
            else -> -1
        }
    }
}