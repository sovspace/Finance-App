package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.BalanceAndStocks
import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.BalanceStockEntity


@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBalance(balance: BalanceEntity)

    @Update
    suspend fun updateBalance(balance: BalanceEntity)

    @Transaction
    @Query("SELECT * FROM balance WHERE user_token = :userToken")
    suspend fun getBalanceStocks(userToken: String): BalanceAndStocks


}
