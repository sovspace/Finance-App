package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class LoginRequest(
    @SerializedName("login")
    var login: String,

    @SerializedName("password")
    var password: String){

}