package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class BalanceHistoryEntity (
    @Embedded
    val balance: BalanceEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "balance_id"
    )

    val history: List<BalanceHistoryEntity>
)