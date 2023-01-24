package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.CoinTypeRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.GradeRepository

class HuntActivityViewModel(application: Application): AndroidViewModel(application) {
    private val gradeRepository = GradeRepository(application)
    private val coinTypeRepository = CoinTypeRepository(application)

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

    fun getListSortedByCoinType(): List<Find> {
        return listOfFinds
    }

    fun getAllCoinTypes(): LiveData<List<CoinType>> {
        return coinTypeRepository.getCoinTypes()
    }
}