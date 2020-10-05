package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("login")
    var name: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("first_name")
    var firstName: String,

    @SerializedName("last_name")
    var lastName: String
)

