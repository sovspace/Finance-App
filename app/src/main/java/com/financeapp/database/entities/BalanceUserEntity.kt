package com.financeapp.database.entities

import androidx.room.Embedded
import androidx.room.Relation

class BalanceUserEntity(

    @Embedded
    val user: UserEntity,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "balance_id"
    )
    val balance: BalanceEntity

)