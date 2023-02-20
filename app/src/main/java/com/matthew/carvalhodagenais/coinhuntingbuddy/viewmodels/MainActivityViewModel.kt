package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.HuntGroup
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.CoinTypeRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.HuntGroupRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.HuntRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val huntGroupRepository = HuntGroupRepository(application)
    private val huntRepository = HuntRepository(application)
    private val coinTypeRepository = CoinTypeRepository(application)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var currentHuntGroup: HuntGroup? = null

    val allHuntGroups = huntGroupRepository.getHuntGroups()

    fun getHuntType(huntGroup: HuntGroup): Deferred<MutableList<CoinType>> = coroutineScope.async(Dispatchers.IO) {
        val hunts = huntRepository.getHuntsByHuntGroupId(huntGroup.id)
        val distinctCoinTypes = hunts.distinctBy { it.coinTypeId }

        val coinTypeList = mutableListOf<CoinType>()
        distinctCoinTypes.forEach {
            val ct = coinTypeRepository.getCoinTypeById(it.coinTypeId)
            coinTypeList.add(ct)
        }

        return@async coinTypeList
    }

    fun setCurrentHuntGroup(huntGroup: HuntGroup) {
        currentHuntGroup = huntGroup
    }

    fun getCurrentHuntGroup(): HuntGroup? {
        return currentHuntGroup
    }
}