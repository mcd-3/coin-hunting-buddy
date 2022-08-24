package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.FindRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.GradeRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.RegionRepository

class HuntActivityViewModel(application: Application): AndroidViewModel(application) {
    private val regionRepository = RegionRepository(application)
    private val gradeRepository = GradeRepository(application)
    private val findRepository = FindRepository(application)

    val listOfFinds = mutableListOf<Find>()


    fun getGrades(): LiveData<List<Grade>> {
        return gradeRepository.getGrades()
    }

    fun addFindToList(
        year: String,
        grade: String,
        variety: String,
        error: String
    ) {
        // TODO: Map the params to a new find object
    }

    fun removeFindFromList(listIndex: Int) {
        // TODO: Remove item from list
    }

    fun getListOfFindsByCoinType() {
        // TODO: Return list of finds by coin type
    }

}