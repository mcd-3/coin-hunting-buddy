package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.HuntDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Hunt

class HuntRepository(application: Application) {

    private var huntDAO: HuntDAO

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        huntDAO = appDatabase.huntDao()
    }

    fun insert(hunt: Hunt): Long {
        return huntDAO.insert(hunt)
    }

}