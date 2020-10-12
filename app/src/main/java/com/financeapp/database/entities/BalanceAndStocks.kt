package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation


class BalanceAndStocks(
    @Embedded
    val balance: BalanceEntity,

    @Relation(
        parentColumn = "user_token",
        entityColumn = "stock_user_token"
    )

    val stocks: List<BalanceStockEntity>
)