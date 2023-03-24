package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.HuntGroupDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.RegionDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Hunt
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import java.util.*

class HuntGroupRepository(application: Application) {

    private var huntGroupDAO: HuntGroupDAO
    private var regionDAO: RegionDAO
    private var allHuntGroups: LiveData<List<HuntGroup>>

    init {
        val appDatabase: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        huntGroupDAO = appDatabase.huntGroupDao()
        regionDAO = appDatabase.regionDao()
        allHuntGroups = huntGroupDAO.getHuntGroups()
    }

    fun getHuntGroups(): LiveData<List<HuntGroup>> {
        return allHuntGroups
    }

    fun getHuntGroupsSync(): List<HuntGroup> {
        return huntGroupDAO.getHuntGroupsSync()
    }

    fun getHuntGroupsByRecent(): LiveData<List<HuntGroup>> {
        return huntGroupDAO.getHuntGroupsOrderedRecent()
    }

    fun getHuntGroupsByOlder(): LiveData<List<HuntGroup>> {
        return huntGroupDAO.getHuntGroupsOrderedOlder()
    }

    fun insert(regionCode: String): Long {
        val region = regionDAO.getRegionByCode(regionCode.lowercase())
        val hg = HuntGroup(dateHunted = Date(), regionId = region.id)
        return huntGroupDAO.insert(hg)
    }

    suspend fun delete(hg: HuntGroup) {
        huntGroupDAO.delete(hg)
    }

    fun getDateHuntedByHuntId(huntId: Int): LiveData<Date> {
        return huntGroupDAO.getDateHuntedByHuntId(huntId)
    }
}