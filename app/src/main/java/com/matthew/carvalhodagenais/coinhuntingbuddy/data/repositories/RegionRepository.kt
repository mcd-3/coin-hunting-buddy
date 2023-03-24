package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.RegionDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Region
import kotlinx.coroutines.coroutineScope

class RegionRepository(application: Application) {

    private var regionDao: RegionDAO
    private var allRegions: LiveData<List<Region>>
    private var allRegionCodes: LiveData<List<String>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        regionDao = database.regionDao()
        allRegions = regionDao.getRegions()
        allRegionCodes = regionDao.getRegionCodes()
    }

    fun getRegions(): LiveData<List<Region>> {
        return allRegions
    }

    fun getRegionCodes(): LiveData<List<String>> {
        return allRegionCodes
    }

    fun getRegionById(id: Int): LiveData<Region> {
        return regionDao.getRegionById(id)
    }

    fun getRegionNameById(id: Int): String {
        return regionDao.getRegionNameById(id)
    }

    suspend fun getRegionByCode(code: String): Region = coroutineScope{
        regionDao.getRegionByCodeAsync(code)
    }

}

