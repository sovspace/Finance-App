package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class StockInfoRequest(
    @SerializedName("stock_symbol")
    val stockSymbol: String)