package com.financeapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.financeapp.database.entities.BalanceStockEntity

@Dao
interface BalanceStockDao {

    @Delete
    suspend fun deleteBalanceStock(balanceStock: BalanceStockEntity)

    @Update
    suspend fun updateBalanceStock(balanceStock: BalanceStockEntity)

}