package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.BalanceStockEntity


@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBalance(balance: BalanceEntity)

    @Update
    suspend fun updateBalance(balance: BalanceEntity)

    @Query("SELECT * FROM balance WHERE balance_id = :balanceId")
    suspend fun getBalanceStocks(balanceId: Int): List<BalanceStockEntity>


}
