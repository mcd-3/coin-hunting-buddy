package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.enums.DateFilter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.CSVWriter
import com.matthew.carvalhodagenais.coinhuntingbuddy.utils.DateToStringConverter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

class MainActivityViewModel(application: Application): AndroidViewModel(application) {
    private val huntGroupRepository = HuntGroupRepository(application)
    private val huntRepository = HuntRepository(application)
    private val coinTypeRepository = CoinTypeRepository(application)
    private val findRepository = FindRepository(application)
    private val gradeRepository = GradeRepository(application)
    private val regionRepository = RegionRepository(application)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var currentHuntGroup: HuntGroup? = null

    private val isLoadingFlow = MutableStateFlow(true)
    val isLoading = isLoadingFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            isLoadingFlow.value = false
        }
    }

    var dateFilter = DateFilter.UNSET
    var findsDateFilter = DateFilter.UNSET
    var coinTypeFilter: CoinType? = null

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

    fun getAllFindsFiltered(dateFilter: DateFilter, coinTypeFilter: CoinType?): LiveData<List<Find>> {
        return if (dateFilter != DateFilter.UNSET && coinTypeFilter != null) {
            if (dateFilter == DateFilter.OLDEST) {
                findRepository.getFindsByCoinType(coinTypeFilter)
            } else {
                findRepository.getFindsByCoinTypeNewest(coinTypeFilter)
            }
        } else if (dateFilter != DateFilter.UNSET) {
            if (dateFilter == DateFilter.OLDEST) {
               findRepository.getFindsOlder()
            } else {
                findRepository.getFinds()
            }
        } else if (coinTypeFilter != null) {
            findRepository.getFindsByCoinTypeNewest(coinTypeFilter)
        } else {
            findRepository.getFinds()
        }
    }

    fun getDateHuntedForFind(huntId: Int): LiveData<Date> {
        return huntGroupRepository.getDateHuntedByHuntId(huntId)
    }

    fun getAllCoinTypes(): LiveData<List<CoinType>> {
        return coinTypeRepository.getCoinTypes()
    }

    fun isSupportedCoinType(ct: CoinType): Boolean {
        return ct.id != CoinType.CT_CANADA_DOLLAR_LARGE && ct.id != CoinType.CT_CANADA_HD
    }

    suspend fun deleteHunt(huntGroup: HuntGroup?) {
        coroutineScope.launch {
            if (huntGroup != null) {
                val hunts = huntRepository.getHuntsByHuntGroupId(huntGroup.id)

                hunts.forEach { hunt ->
                    findRepository.getFindsByHuntIdSync(hunt.id).forEach { find ->
                        findRepository.delete(find)
                    }
                    huntRepository.delete(hunt)
                }

                // Delete hunt group
                huntGroupRepository.delete(huntGroup)
            }
        }
    }

    /**
     * Gets a map of all finds data
     */
    suspend fun getFindsData(): List<Map<String, Any?>> = coroutineScope.async(Dispatchers.IO) {
        val dataList = mutableListOf<Map<String, Any?>>()
        var i = 0

        // TODO: Optimize this
        huntGroupRepository.getHuntGroupsSync().forEach { hg ->
            huntRepository.getHuntsByHuntGroupId(hg.id).forEach { hunt ->
                findRepository.getFindsByHuntIdSync(hunt.id).forEach { find ->
                    val map = CSVWriter.createDataMap(
                        dateFound = DateToStringConverter.getString(hg.dateHunted),
                        year      = if (find.year == null) "" else find.year.toString(),
                        error     = if (find.error == null) "" else find.error.toString(),
                        variety   = if (find.variety == null) "" else find.variety.toString(),
                        mintMark  = if (find.mintMark == null) "" else find.mintMark.toString(),
                        coinType  = coinTypeRepository.getCoinTypeById(find.coinTypeId).name,
                        grade     = if (find.gradeId == null) "" else gradeRepository.getGradeByIdSync(find.gradeId!!).code,
                        region    = regionRepository.getRegionNameById(hg.regionId),
                    )

                    dataList.add(i, map)
                    i++
                }
            }
        }
        return@async dataList
    }.await()

    /**
     * This will delete all app data.
     * Make sure the user is warned!
     */
    fun deleteData(): Deferred<Boolean> = coroutineScope.async(Dispatchers.IO) {
        // Delays are used in this function to show the user that the app is doing something
        try {
            delay(1000)
            val hgs = huntGroupRepository.getHuntGroupsSync()
            hgs.forEach {hg ->
                huntRepository.getHuntsByHuntGroupId(hg.id).forEach { hunt ->
                    findRepository.deleteByHuntId(hunt.id)
                    huntRepository.delete(hunt)
                }
                huntGroupRepository.delete(hg)
            }
            delay(1000)
        } catch (e: Exception) {
            return@async false
        }
        return@async true
    }
}