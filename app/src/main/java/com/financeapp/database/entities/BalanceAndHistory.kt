package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class BalanceAndHistory (
    @Embedded
    val balance: BalanceEntity,

    @Relation(
        parentColumn = "user_token",
        entityColumn = "user_token"
    )

    val history: List<BalanceHistoryEntryEntity>
)