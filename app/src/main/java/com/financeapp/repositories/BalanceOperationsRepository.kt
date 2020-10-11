package com.financeapp.repositories

import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.models.BuyRequest
import com.financeapp.webservice.models.RefillRequest

class BalanceOperationsRepository(token: String) : BaseActionsRepository(token) {

    suspend fun buyStock(amount: Double, stockSymbol: String): Resource<String> {
        return saveApiCallResource { authenticateService.buyStock(BuyRequest(stockSymbol, amount)) }
    }

    suspend fun refillStock(amount: Double): Resource<String>{
        return saveApiCallResource { authenticateService.refill(RefillRequest(amount)) }
    }

}