package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class StockPredictionEntry (

    @SerializedName("date")
    var date: Date,

    @SerializedName("predicted_price")
    var predictedPrice: Double,

    @SerializedName("upper_price")
    var upperPrice: Double,

    @SerializedName("lower_price")
    var lowerPrice: Double)