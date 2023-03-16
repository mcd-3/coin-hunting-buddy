package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
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

    fun getHuntsByHuntGroupId(hgId: Int): List<Hunt> {
        return huntDAO.getHuntsByHuntGroupId(hgId)
    }

    fun getLiveHuntsByHuntGroupId(hgId: Int): LiveData<List<Hunt>> {
        return huntDAO.getLiveHuntsByHuntGroupId(hgId)
    }

    suspend fun delete(hunt: Hunt) {
        huntDAO.delete(hunt)
    }

}