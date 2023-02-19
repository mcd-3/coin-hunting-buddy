package com.matthew.carvalhodagenais.coinhuntingbuddy.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

/**
 * Utility method to run database threads
 */
fun runOnDBThread(function: () -> Unit) {
    Executors.newSingleThreadExecutor().execute(function)
}

// Database written using the Room library
@Database(version = 2,
    exportSchema = false,
    entities = [
        Region::class,
        Grade::class,
        Hunt::class,
        HuntGroup::class,
        Find::class,
        CoinType::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun regionDao(): RegionDAO
    abstract fun gradeDao(): GradeDAO
    abstract fun coinTypeDao(): CoinTypeDAO
    abstract fun huntGroupDao(): HuntGroupDAO
    abstract fun findDao(): FindDAO

    companion object {

        // Instance of the database object
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "crh_database")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        runOnDBThread {
                            val instance = getInstance(context)
                            insertInitialData(instance)
                        }
                    }
                })
                .build()

        /**
         * Insert initial regions, grades, and coin types
         */
        private fun insertInitialData(instance: AppDatabase) {
            // Insert Regions
            instance.regionDao().insert(
                Region(Region.REGION_CANADA_ID, "ca", "Canada"))
            instance.regionDao().insert(
                Region(Region.REGION_USA_ID, "us", "U.S.A"))

            // Insert Grades
            instance.gradeDao().insert(
                Grade(Grade.GRADE_PO_ID, "PO", "Poor"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_FR_ID, "FR", "Fair"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_AG_ID, "AG", "About Good"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_G_ID, "G", "Good"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_F_ID, "F", "Fine"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_VF_ID, "VF", "Very Fine"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_AU_ID, "AU", "About Uncirculated"))
            instance.gradeDao().insert(
                Grade(Grade.GRADE_MS_ID, "MS", "Mint State"))

            // Insert Coin Types
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_PENNY, "1 Cent", 0.01, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_NICKEL, "5 Cents", 0.05, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_DIME, "10 Cents", 0.1, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_QUARTER, "25 Cents", 0.25, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_HD, "50 Cents", 0.50, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_DOLLAR_LARGE, "1 Dollar (Large)", 1.0, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_DOLLAR_LOONIE, "1 Dollar (\"Loonie\")", 1.0, Region.REGION_CANADA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_CANADA_TOONIE, "2 Dollars (\"Toonie\")", 2.0, Region.REGION_CANADA_ID))

            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_PENNY, "Penny", 0.01, Region.REGION_USA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_NICKEL, "Nickel", 0.05, Region.REGION_USA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_DIME, "Dime", 0.1, Region.REGION_USA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_QUARTER, "Quarter", 0.25, Region.REGION_USA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_HD, "Half-Dollar", 0.50, Region.REGION_USA_ID))
            instance.coinTypeDao().insert(
                CoinType(CoinType.CT_USA_DOLLAR, "Dollar", 1.0, Region.REGION_USA_ID))

            Log.d("DB", "Database has been successfully populated")
        }
    }
}