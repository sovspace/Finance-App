package com.financeapp.database.entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date


@Entity(tableName = "balance_history_entry")
class BalanceHistoryEntryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "total_cost")
    val totalCost: Double,

    @ColumnInfo(name = "stock_name")
    val stockName: Double,

    @ColumnInfo(name = "operation_time")
    val operationTime: Date,

    @ColumnInfo(name = "history_balance_id")
    val balanceId: Int

)