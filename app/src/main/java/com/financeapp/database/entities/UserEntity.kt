package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financeapp.models.User
import java.sql.Date
import java.util.*


@Entity(tableName = "user")
class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long?
) {
    companion object {
        fun getEntity(token: String, user: User) = UserEntity(
            token,
            user.userInfo.username,
            user.userInfo.firstName,
            user.userInfo.lastName,
            user.userInfo.email,
            user.avatarUrl,
            user.lastUpdated
        )
    }
}