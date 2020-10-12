package com.financeapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import com.financeapp.models.BalanceHistoryEntry

@Dao
interface BalanceHistoryEntryDao {

    @Insert
    suspend fun insertBalanceStock(balanceHistoryEntry: BalanceHistoryEntry)

    @Update
    suspend fun updateBalanceStock(balanceHistoryEntry: BalanceHistoryEntry)
}