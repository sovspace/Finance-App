package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "balance")
data class BalanceEntity(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "currency")
    val currency: Currency,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Date
)