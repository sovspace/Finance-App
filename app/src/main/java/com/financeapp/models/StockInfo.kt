package com.financeapp.models

import com.google.gson.annotations.SerializedName


data class StockInfo(
    @SerializedName("stock_symbol")
    var stockSymbol: String,

    @SerializedName("history")
    var history: Array<StockHistoryEntry>,

    @SerializedName("predictions")
    var predictions: Array<StockPredictionEntry>)
