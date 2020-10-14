package com.financeapp.models

import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.BalanceStockEntity
import com.google.gson.annotations.SerializedName

data class Balance(

    @SerializedName("amount")
    var amount: Double,

    @SerializedName("stocks")
    var stocks: List<BalanceStock>,

    @SerializedName("currency")
    var currency: String,

    @SerializedName("last_updated")
    var lastUpdated: Long){
    companion object{
        fun getFromEntity(balanceEntity: BalanceEntity, balanceStockEntities: List<BalanceStockEntity>): Balance {
            val balanceStocks = ArrayList<BalanceStock>()
            for (entity in balanceStockEntities) {
                balanceStocks.add(BalanceStock.getFromEntity(entity))
            }
            return Balance(balanceEntity.amount, balanceStocks, balanceEntity.currency, balanceEntity.lastUpdated)
        }
    }
}