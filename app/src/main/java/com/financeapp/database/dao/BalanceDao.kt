package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.*


@Dao
interface BalanceDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalanceAndBalanceStocks(balance: BalanceEntity, stocks: List<BalanceStockEntity>)

    @Transaction
    @Update
    suspend fun updateBalanceAndBalanceStocks(balance: BalanceEntity, stocks: List<BalanceStockEntity>)

    @Transaction
    @Query("SELECT * FROM balance WHERE user_token = :userToken")
    suspend fun getBalanceStocks(userToken: String): BalanceAndStocks?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalanceHistory(stocks: List<BalanceHistoryEntryEntity>)

    @Transaction
    @Update
    suspend fun updateBalanceHistory(stocks: List<BalanceHistoryEntryEntity>)

    @Transaction
    @Query("SELECT * FROM balance WHERE user_token = :userToken")
    suspend fun getBalanceHistory(userToken: String): BalanceAndHistory?


    @Query("DELETE FROM balance_stock WHERE balance_stock_id = :balanceStockId")
    suspend fun deleteBalanceStock(balanceStockId: Int)

}
