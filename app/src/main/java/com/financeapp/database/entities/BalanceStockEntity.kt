package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.*

@Entity(tableName = "balance_stock")
class BalanceStockEntity(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "purchase_price")
    val averagePurchasePrice: Double,

    @ColumnInfo(name = "current_price")
    val currentPrice: Double,

    @ColumnInfo(name = "last_updated")
    val lastUpdatedTime: Date)