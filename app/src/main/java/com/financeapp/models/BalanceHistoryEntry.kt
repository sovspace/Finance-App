package com.financeapp.models

import com.financeapp.database.entities.BalanceHistoryEntryEntity
import com.google.gson.annotations.SerializedName
import java.util.*

data class BalanceHistoryEntry(
    @SerializedName("id")
    var id: Int,

    @SerializedName("type")
    var type: String,

    @SerializedName("amount")
    var amount: Int?,

    @SerializedName("total_cost")
    var totalCost: Double,

    @SerializedName("stock_name")
    var stockName: String?,

    @SerializedName("operation_time")
    var operationTime: Date
) {
    companion object {
        fun getFromEntity(balanceHistoryEntryEntity: BalanceHistoryEntryEntity): BalanceHistoryEntry {
            return BalanceHistoryEntry(
                balanceHistoryEntryEntity.id,
                balanceHistoryEntryEntity.type,
                balanceHistoryEntryEntity.amount,
                balanceHistoryEntryEntity.totalCost,
                balanceHistoryEntryEntity.stockName,
                balanceHistoryEntryEntity.operationTime
            )
        }

        fun getFromEntities(balanceHistoryEntryEntities: List<BalanceHistoryEntryEntity>): List<BalanceHistoryEntry> {
            val entries = ArrayList<BalanceHistoryEntry>()
            for (entity in balanceHistoryEntryEntities) {
                entries.add(BalanceHistoryEntry.getFromEntity(entity))
            }
            return entries
        }
    }
}