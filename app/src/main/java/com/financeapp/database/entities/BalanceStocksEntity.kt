package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


@Entity
class BalanceStocksEntity(
    @Embedded
    val balance: BalanceEntity,

    @Relation(
        parentColumn = "balance_id",
        entityColumn = "stock_balance_id"
    )

    val stocks: List<BalanceStocksEntity>
)