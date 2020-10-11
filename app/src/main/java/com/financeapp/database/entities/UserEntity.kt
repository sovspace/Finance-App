package com.financeapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import com.financeapp.models.Balance
import java.sql.Date


@Entity(tableName = "user")
class UserEntity(

    @ColumnInfo(name = "user_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Date,

    @ColumnInfo(name = "token")
    val token: String
)