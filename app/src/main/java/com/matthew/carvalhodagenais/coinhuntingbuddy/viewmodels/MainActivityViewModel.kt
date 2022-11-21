package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.HuntGroupRepository

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val huntGroupRepository = HuntGroupRepository(application)

    val allHuntGroups = huntGroupRepository.getHuntGroups()
}