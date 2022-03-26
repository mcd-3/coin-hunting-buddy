package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.HuntGroupDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup

class HuntGroupRepository(application: Application) {

    private var huntGroupDAO: HuntGroupDAO
    private var allHuntGroups: LiveData<List<HuntGroup>>

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        huntGroupDAO = appDatabase.huntGroupDao()
        allHuntGroups = huntGroupDAO.getHuntGroups()
    }

}