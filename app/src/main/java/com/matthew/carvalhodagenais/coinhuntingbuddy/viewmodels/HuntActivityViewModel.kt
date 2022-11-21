package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
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

    // TODO: Change me
    private val currentHuntId: Int = 0

    val listOfFinds = mutableListOf<Find>()
    private val huntRegionState = mutableStateOf("")

    fun getGrades(): LiveData<List<Grade>> {
        return gradeRepository.getGrades()
    }

    fun setRegion(region: String) {
        huntRegionState.value = region
    }

    fun getRegion(): String {
        return huntRegionState.value
    }

}