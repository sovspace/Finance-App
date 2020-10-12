package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.UserEntity
import com.financeapp.models.BalanceHistoryEntry

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE token = :token")
    suspend fun getUserInfo(token: String): UserEntity

    @Query("SELECT * FROM user WHERE token =:token")
    suspend fun getUserBalance(token: String): BalanceEntity

    @Query("SELECT * FROM user WHERE token = :token")
    suspend fun getUserBalanceHistory(token: String): List<BalanceHistoryEntry>
}