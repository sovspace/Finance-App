package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class BalanceHistoryEntry(
    @SerializedName("type")
    var type: String,

    @SerializedName("amount")
    var amount: Int?,

    @SerializedName("total_cost")
    var totalCost: Double,

    @SerializedName("stock_name")
    var stockName: String?,

    @SerializedName("operation_time")
    var operationTime: Date)