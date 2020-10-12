package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class BalanceHistoryEntity (
    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "token",
        entityColumn = "user_token"
    )

    val history: List<BalanceHistoryEntryEntity>
)