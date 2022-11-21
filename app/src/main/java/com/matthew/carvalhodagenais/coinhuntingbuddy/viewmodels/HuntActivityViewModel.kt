package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.CoinType
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Find
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.Grade
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.FindRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.GradeRepository
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.repositories.RegionRepository

class HuntActivityViewModel(application: Application): AndroidViewModel(application) {
    private val regionRepository = RegionRepository(application)
    private val gradeRepository = GradeRepository(application)
    private val findRepository = FindRepository(application)

    val listOfGrades: LiveData<List<Grade>> = gradeRepository.getGrades()

    // TODO: Change me to most recent HuntGroupId when saving
    private val currentHuntGroupId: Int = 0

    val listOfFinds = mutableListOf<Find>()
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
                gradeId = if (grade.isNullOrEmpty()) null else findType,
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
}