package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Balance(

    @SerializedName("amount")
    var amount: Double,

    @SerializedName("stocks")
    var stocks: List<BalanceStock>,

    @SerializedName("currency")
    var currency: Currency,

    @SerializedName("last_updated")
    var lastUpdated: Long)