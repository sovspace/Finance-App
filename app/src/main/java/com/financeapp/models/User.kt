package com.financeapp.models

import com.google.gson.annotations.SerializedName
import java.util.*


data class User(
    @SerializedName("user")
    var userInfo: UserInfo,

    @SerializedName("last_updated")
    var lastUpdated: Date,

    @SerializedName("avatar_url")
    var avatarUrl: String) {

    inner class UserInfo(
        @SerializedName("email")
        var email: String,

        @SerializedName("username")
        var username: String,

        @SerializedName("first_name")
        var firstName: String,

        @SerializedName("last_name")
        var lastName: String?)

}