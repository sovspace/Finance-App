package com.financeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.financeapp.database.entities.BalanceStockEntity

@Dao
interface BalanceStockDao {

    @Insert
    suspend fun insertBalanceStock(balanceStock: BalanceStockEntity)

    @Update
    suspend fun updateBalanceStock(balanceStock: BalanceStockEntity)

}