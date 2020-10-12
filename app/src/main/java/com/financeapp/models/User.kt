package com.financeapp.models

import com.financeapp.database.entities.UserEntity
import com.financeapp.models.User.UserInfo
import com.google.gson.annotations.SerializedName
import java.util.*


data class User(
    @SerializedName("user")
    var userInfo: UserInfo,

    @SerializedName("last_updated")
    var lastUpdated: Long,

    @SerializedName("avatar_url")
    var avatarUrl: String?
) {

     class UserInfo(
        @SerializedName("email")
        var email: String?,

        @SerializedName("username")
        var username: String?,

        @SerializedName("first_name")
        var firstName: String?,

        @SerializedName("last_name")
        var lastName: String?
    )

    companion object {
        fun getFromEntity(userEntity: UserEntity) = User(
            UserInfo(
                userEntity.email,
                userEntity.username,
                userEntity.firstName,
                userEntity.lastName
            ),
            userEntity.lastUpdated as Long,
            userEntity.avatarUrl
        )
    }
}