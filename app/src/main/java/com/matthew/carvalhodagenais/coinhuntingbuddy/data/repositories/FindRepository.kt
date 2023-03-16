package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.FindDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
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

    fun getFindsOlder(): LiveData<List<Find>> {
        return findDAO.getAllFindsOlder()
    }

    fun getFindsByHuntId(id: Int): LiveData<List<Find>> {
        return findDAO.getFindByHuntId(id)
    }

    fun getFindsByHuntIdSync(id: Int): List<Find> {
        return findDAO.getFindByHuntIdSync(id)
    }

    fun getFindsByCoinType(coinType: CoinType): LiveData<List<Find>> {
        return findDAO.getFindsByCoinTypeId(coinType.id)
    }

    fun getFindsByCoinTypeNewest(coinType: CoinType): LiveData<List<Find>> {
        return findDAO.getFindsByCoinTypeIdNewest(coinType.id)
    }

    suspend fun insert(find: Find) {
        findDAO.insert(find)
    }

    suspend fun delete(find: Find) {
        findDAO.delete(find)
    }
}