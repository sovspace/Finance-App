package com.financeapp.repositories

import com.financeapp.models.Balance
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.SellRequest


class BalanceRepository(token:String): BaseActionsRepository(token) {

    suspend fun getBalance() : Resource<Balance> {
        return saveApiCallResource {  authenticateService.getBalance(LastUpdatedRequest(null))}
    }

    suspend fun sellStock(stockId: Int): Resource<String>{

        return saveApiCallResource { authenticateService.sellStock(SellRequest(stockId)) }
    }


}