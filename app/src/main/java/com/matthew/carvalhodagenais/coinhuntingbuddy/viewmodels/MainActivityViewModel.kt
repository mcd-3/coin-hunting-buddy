package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val huntGroupRepository = HuntGroupRepository(application)
    private val huntRepository = HuntRepository(application)
    private val coinTypeRepository = CoinTypeRepository(application)
    private val findRepository = FindRepository(application)
    private val gradeRepository = GradeRepository(application)

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

    fun getHuntsByHuntGroup(huntGroup: HuntGroup): LiveData<List<Hunt>> {
        return huntRepository.getLiveHuntsByHuntGroupId(huntGroup.id)
    }

    fun getCoinTypeNameById(id: Int): LiveData<String> {
        return coinTypeRepository.getCoinTypeNameById(id)
    }

    fun getFindsByHunt(hunt: Hunt): LiveData<List<Find>> {
        return findRepository.getFindsByHuntId(hunt.id)
    }

    fun getGradeById(id: Int): LiveData<Grade> {
        return gradeRepository.getGradeById(id)
    }

    fun getAllHuntGroups(dateFilter: DateFilter): LiveData<List<HuntGroup>> {
        return when(dateFilter) {
            DateFilter.OLDEST -> huntGroupRepository.getHuntGroupsByOlder()
            else -> huntGroupRepository.getHuntGroupsByRecent()
        }
    }

    suspend fun deleteHunt() {

    }
}