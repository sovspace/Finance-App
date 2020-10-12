package com.financeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.financeapp.database.entities.BalanceStocksEntity

@Dao
interface BalanceStockDao {

    @Insert
    suspend fun insertBalanceStock(balanceStock: BalanceStocksEntity)

    @Update
    suspend fun updateBalanceStock(balanceStock: BalanceStocksEntity)

}