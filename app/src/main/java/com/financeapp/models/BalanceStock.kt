package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

class BalanceStock(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("amount")
    var amount: Int,

    @SerializedName("purchase_price")
    var averagePurchasePrice: Double,

    @SerializedName("current_price")
    var currentPrice: Double,

    @SerializedName("last_updated")
    var lastUpdatedTime: Date)