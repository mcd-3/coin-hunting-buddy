package com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.AppDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.CoinTypeDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.GradeDAO
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade

class CoinTypeRepository(application: Application) {

    private var coinTypeDAO: CoinTypeDAO
    private var allCoinTypes: LiveData<List<CoinType>>

    init {
        val database: AppDatabase = AppDatabase.getInstance(
            application.applicationContext
        )!!
        coinTypeDAO = database.coinTypeDao()
        allCoinTypes = coinTypeDAO.getCoinTypes()
    }

    fun getCoinTypes(): LiveData<List<CoinType>> {
        return allCoinTypes
    }

    fun getCoinTypeById(id: Int): CoinType {
        return coinTypeDAO.getCoinTypeById(id)
    }

    fun getCoinTypeNameById(id: Int): LiveData<String> {
        return coinTypeDAO.getCoinTypeNameById(id)
    }
}