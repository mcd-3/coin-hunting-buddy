package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.FindDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find

class FindRepository(application: Application) {

    private var findDAO: FindDAO
    private var allFinds: LiveData<List<Find>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        findDAO = database.findDao()
        allFinds = findDAO.getAllFinds()
    }

    fun getFinds(): LiveData<List<Find>> {
        return allFinds
    }

    fun getFindsByHuntId(id: Int): LiveData<List<Find>> {
        return findDAO.getFindByHuntId(id)
    }

    suspend fun insert(find: Find) {
        findDAO.insert(find)
    }
}