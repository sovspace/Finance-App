package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class StockHistoryEntry(
    @SerializedName("date")
    var date: Date,
    @SerializedName("price")
    var price: Double)
