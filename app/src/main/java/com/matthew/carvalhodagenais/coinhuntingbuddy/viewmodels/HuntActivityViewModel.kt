package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Hunt
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HuntActivityViewModel(application: Application): AndroidViewModel(application) {
    private val gradeRepository = GradeRepository(application)
    private val huntGroupRepository = HuntGroupRepository(application)
    private val coinTypeRepository = CoinTypeRepository(application)
    private val huntRepository = HuntRepository(application)
    private val findRepository = FindRepository(application)

    val listOfFinds = mutableListOf<Find>()
    var rollsPerCoin: MutableMap<String, Int> = mutableMapOf()
    private val huntRegionState = mutableStateOf("")

    fun getListOfGradeCodes(): List<String> {
        return gradeRepository.getGradeCodesCached()
    }

    fun setRegion(region: String) {
        huntRegionState.value = region
    }

    fun getRegion(): String {
        return huntRegionState.value
    }

    fun getGradeStringById(id: Int?): String {
        return gradeRepository.getGradeCodeByIdCached(id)
    }

    /**
     * Converts a coin string to coin type
     *
     * @param str String - String to convert to CoinType enum
     * @return CoinTypes
     */
    fun getCoinTypeFromString(str: String): Int {
        return when (str.lowercase()) {
            "1 cents" -> CoinType.CT_CANADA_PENNY
            "5 cents" -> CoinType.CT_CANADA_NICKEL
            "10 cents" -> CoinType.CT_CANADA_DIME
            "25 cents" -> CoinType.CT_CANADA_QUARTER
            "loonies" -> CoinType.CT_CANADA_DOLLAR_LOONIE
            "toonies" -> CoinType.CT_CANADA_TOONIE
            "pennies" -> CoinType.CT_USA_PENNY
            "nickels" -> CoinType.CT_USA_NICKEL
            "dimes" -> CoinType.CT_USA_DIME
            "quarters" -> CoinType.CT_USA_QUARTER
            "half-dollars" -> CoinType.CT_USA_HD
            "dollars" -> CoinType.CT_USA_DOLLAR
            else -> -1 // Invalid
        }
    }

    fun addFindToList(
        year: String? = null,
        mintMark: String? = null,
        variety: String? = null,
        error: String? = null,
        grade: String? = null,
        findType: Int
    ) {
        listOfFinds.add(
            Find(
                year = if (year.isNullOrEmpty()) null else year.toShort(),
                variety = if (variety.isNullOrEmpty()) null else variety,
                error = if (error.isNullOrEmpty()) null else error,
                mintMark = if (mintMark.isNullOrEmpty()) null else mintMark,
                gradeId = if (grade.isNullOrEmpty()) null else gradeRepository.getGradeIdByCodeCached(grade),
                coinTypeId = findType,
                huntId = -1
            )
        )
    }

    fun getListOfFindsByCoinType(coinTypeInt: Int): MutableList<Find> {
        val filteredList = mutableListOf<Find>()
        listOfFinds.forEach {
            if (it.coinTypeId == coinTypeInt) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    private fun getListFilteredByCoinType(coinType: Int): List<Find> {
        val filteredList = mutableListOf<Find>()
        listOfFinds.forEach {
            if (it.coinTypeId == coinType) {
                filteredList.add(it)
            }
        }
        return filteredList
    }

    private fun getCoinTypesFromFindsList(): List<Int> {
        val listOfCoinTypes = mutableListOf<Int>()
        listOfFinds.forEach {
            listOfCoinTypes.add(it.coinTypeId)
        }
        return listOfCoinTypes.distinct().toList()
    }

    private fun getNumOfRollsPerCoinType(ct: Int): Int {
        rollsPerCoin.forEach { entry ->
            if (getCoinTypeFromString(entry.key) == ct) {
                return entry.value
            }
        }
        return -1
    }

    fun getAllCoinTypes(): LiveData<List<CoinType>> {
        return coinTypeRepository.getCoinTypes()
    }

    fun getRollCount(): Int {
        var rolls = 0
        rollsPerCoin.forEach {
            rolls += it.value
        }
        return rolls
    }

    fun dateAsString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().format(formatter).toString()
    }

    private suspend fun insertFinds(list: List<Find>, huntId: Int) {
        for (find in list) {
            find.huntId = huntId
            Log.e("FIND", find.toString())
            findRepository.insert(find)
        }
    }

    /**
     * Saves a new HuntGroup with Hunt and Find data into the database
     */
    suspend fun saveData() {
        val hgId = huntGroupRepository.insert()
        val coinTypeList = getCoinTypesFromFindsList()

        for (ct in coinTypeList) {
            val numOfRolls = getNumOfRollsPerCoinType(ct)

            val huntId = huntRepository.insert(
                Hunt(
                    coinTypeId = ct,
                    huntGroupId = hgId.toInt(),
                    numberOfRolls = numOfRolls
                )
            ).toInt()

            insertFinds(getListFilteredByCoinType(ct), huntId)
        }
    }
}