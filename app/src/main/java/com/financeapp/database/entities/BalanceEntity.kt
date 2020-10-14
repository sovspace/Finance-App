package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financeapp.models.Balance
import java.util.*


@Entity(tableName = "balance")
data class BalanceEntity(

    @ColumnInfo(name = "user_token")
    @PrimaryKey
    val userToken: String,

    @ColumnInfo(name = "amount")
    val amount: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long
) {
    companion object{
        fun getEntity(token: String, balance: Balance): BalanceEntity {
            return BalanceEntity(token, balance.amount, balance.currency, balance.lastUpdated)
        }
    }
}