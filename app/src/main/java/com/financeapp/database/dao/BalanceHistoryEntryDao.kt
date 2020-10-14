package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.BalanceHistoryEntryEntity

@Dao
interface BalanceHistoryEntryDao {

    @Insert
    suspend fun insertBalanceStock(balanceHistoryEntry: BalanceHistoryEntryEntity)

    @Update
    suspend fun updateBalanceStock(balanceHistoryEntry: BalanceHistoryEntryEntity)
}