package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.GradeRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.RegionRepository

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val regionRepository = RegionRepository(application)
    private val gradeRepository = GradeRepository(application)
}