package com.financeapp.webservice.models

import com.google.gson.annotations.SerializedName

data class PasswordRequest(
    @SerializedName("current_password")
    var currentPassword: String,

    @SerializedName("new_password")
    var newPassword: String) {



}