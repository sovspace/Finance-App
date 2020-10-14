package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financeapp.models.BalanceHistoryEntry
import java.util.Date


@Entity(tableName = "balance_history_entry")
class BalanceHistoryEntryEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "stock_name")
    val stockName: String?,

    @ColumnInfo(name = "total_cost")
    val totalCost: Double,

    @ColumnInfo(name = "amount")
    val amount: Int?,

    @ColumnInfo(name = "operation_time")
    val operationTime: Date,

    @ColumnInfo(name = "user_token")
    val userToken: String
) {
    companion object {
        fun getEntity(token: String, balanceHistoryEntry: BalanceHistoryEntry): BalanceHistoryEntryEntity {
            return BalanceHistoryEntryEntity(
                balanceHistoryEntry.id,
                balanceHistoryEntry.type,
                balanceHistoryEntry.stockName,
                balanceHistoryEntry.totalCost,
                balanceHistoryEntry.amount,
                balanceHistoryEntry.operationTime,
                token
            )
        }

        fun getEntities(token: String, balanceHistoryEntries: List<BalanceHistoryEntry>): List<BalanceHistoryEntryEntity>{
            val entities = ArrayList<BalanceHistoryEntryEntity>()
            for (entry in balanceHistoryEntries) {
                entities.add(BalanceHistoryEntryEntity.getEntity(token, entry))
            }
            return entities
        }
    }
}