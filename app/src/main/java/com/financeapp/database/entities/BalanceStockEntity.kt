package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financeapp.models.BalanceStock
import java.sql.*

@Entity(tableName = "balance_stock")
class BalanceStockEntity(

    @ColumnInfo(name = "balance_stock_id")
    @PrimaryKey
    val balanceStockId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "amount")
    val amount: Int,

    @ColumnInfo(name = "purchase_price")
    val averagePurchasePrice: Double,

    @ColumnInfo(name = "current_price")
    val currentPrice: Double,

    @ColumnInfo(name = "last_updated")
    val lastUpdatedTime: Long,

    @ColumnInfo(name = "stock_user_token")
    val stockUserToken: String
) {
    companion object {
        fun getEntity(token: String, balanceStock: BalanceStock): BalanceStockEntity {
            return BalanceStockEntity(
                balanceStock.id,
                balanceStock.name,
                balanceStock.amount,
                balanceStock.averagePurchasePrice,
                balanceStock.currentPrice,
                balanceStock.lastUpdatedTime,
                token
            )
        }
    }
}