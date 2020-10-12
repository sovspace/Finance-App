package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "balance")
data class BalanceEntity(

    @ColumnInfo(name = "balance_id")
    @PrimaryKey(autoGenerate = true)
    val balanceId: Int,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Date
)