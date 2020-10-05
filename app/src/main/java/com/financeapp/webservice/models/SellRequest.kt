package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class SellRequest (
    @SerializedName("stock_id")
    val stockId : Int){
}