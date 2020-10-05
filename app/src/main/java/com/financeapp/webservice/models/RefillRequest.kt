package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class RefillRequest (

    @SerializedName("amount")
    val amount: Double
){


}