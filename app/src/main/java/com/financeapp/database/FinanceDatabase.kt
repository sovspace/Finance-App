package com.financeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.financeapp.database.converters.DateConverters
import com.financeapp.database.dao.BalanceDao
import com.financeapp.database.dao.BalanceHistoryEntryDao
import com.financeapp.database.dao.BalanceStockDao
import com.financeapp.database.dao.UserDao
import com.financeapp.database.entities.*

@Database(
    entities = [BalanceEntity::class, BalanceHistoryEntryEntity::class, BalanceStockEntity::class,
        UserEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class FinanceDatabase : RoomDatabase() {

    abstract val balanceDao: BalanceDao
    abstract val balanceHistoryEntryDao: BalanceHistoryEntryDao
    abstract val balanceStockDao: BalanceStockDao
    abstract val userDao: UserDao

    companion object {

        @Volatile
        private var instance: FinanceDatabase? = null

        fun getInstance(context: Context): FinanceDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FinanceDatabase::class.java,
                        "finance_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance as FinanceDatabase
            }
        }
    }
}