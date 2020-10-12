package com.financeapp.database.dao

import androidx.room.*
import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.UserAndBalance
import com.financeapp.database.entities.UserAndBalanceHistory
import com.financeapp.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user WHERE token = :token")
    suspend fun getUserInfo(token: String): UserEntity

    @Transaction
    @Query("SELECT * FROM user WHERE token = :token")
    suspend fun getUserBalance(token: String): UserAndBalance

    @Transaction
    @Query("SELECT * FROM user WHERE token = :token")
    suspend fun getUserBalanceHistory(token: String): UserAndBalanceHistory
}