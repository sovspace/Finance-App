package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class BuyRequest (
    @SerializedName("stock_symbol")
    val stockSymbol : String,

    @SerializedName("amount")
    val amount: Double){

}