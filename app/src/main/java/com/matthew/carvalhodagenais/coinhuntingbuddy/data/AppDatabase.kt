package com.matthew.carvalhodagenais.coinhuntingbuddy.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.daos.*
import com.matthew.carvalhodagenais.coinhuntingbuddy.data.entities.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Database written using the Room library
@Database(version = 1,
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

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "crh_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                        .also {
                            Log.d("DB", it.openHelper.writableDatabase.path)
                        }
                    GlobalScope.launch {
                        insertInitialData()
                    }
                }
            }
            return instance
        }

        /**
         * Insert initial regions, grades, and coin types
         */
        private fun insertInitialData() {
            // Insert Regions
            instance?.regionDao()?.insert(
                Region(1, "ca", "Canada"))
            instance?.regionDao()?.insert(
                Region(2, "us", "U.S.A"))

            // Insert Grades
            instance?.gradeDao()?.insert(
                Grade(1, "PO", "Poor"))
            instance?.gradeDao()?.insert(
                Grade(2, "FR", "Fair"))
            instance?.gradeDao()?.insert(
                Grade(3, "AG", "About Good"))
            instance?.gradeDao()?.insert(
                Grade(4, "G", "Good"))
            instance?.gradeDao()?.insert(
                Grade(5, "F", "Fine"))
            instance?.gradeDao()?.insert(
                Grade(6, "VF", "Very Fine"))
            instance?.gradeDao()?.insert(
                Grade(7, "AU", "About Uncirculated"))
            instance?.gradeDao()?.insert(
                Grade(8, "MS", "Mint State"))

            // Insert Coin Types
            instance?.coinTypeDao()?.insert(
                CoinType(1, "1 Cent", 0.01, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(2, "5 Cents", 0.05, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(3, "10 Cents", 0.1, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(4, "25 Cents", 0.25, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(5, "50 Cents", 0.50, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(6, "1 Dollar (Large)", 1.0, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(7, "1 Dollar (\"Loonie\")", 1.0, 1))
            instance?.coinTypeDao()?.insert(
                CoinType(8, "2 Dollars (\"Toonie\")", 2.0, 1))

            instance?.coinTypeDao()?.insert(
                CoinType(9, "Penny", 0.01, 2))
            instance?.coinTypeDao()?.insert(
                CoinType(10, "Nickel", 0.05, 2))
            instance?.coinTypeDao()?.insert(
                CoinType(11, "Dime", 0.1, 2))
            instance?.coinTypeDao()?.insert(
                CoinType(12, "Quarter", 0.25, 2))
            instance?.coinTypeDao()?.insert(
                CoinType(13, "Half-Dollar", 0.50, 2))
            instance?.coinTypeDao()?.insert(
                CoinType(14, "Dollar", 1.0, 2))

            Log.d("DB", "Database has been successfully populated")
        }
    }
}