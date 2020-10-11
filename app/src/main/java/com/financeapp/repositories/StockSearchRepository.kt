package com.financeapp.repositories

import com.financeapp.models.StockInfo
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource

class StockSearchRepository(token: String): BaseActionsRepository(token) {


    suspend fun getStockInfo(stockSymbol: String) : Resource<StockInfo> {
        return saveApiCallResource { authenticateService.getStockInfo(stockSymbol) }
    }

}