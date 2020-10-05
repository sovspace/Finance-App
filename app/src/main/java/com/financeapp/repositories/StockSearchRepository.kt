package com.financeapp.repositories

import com.financeapp.models.StockInfo
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCall
import java.net.ConnectException
import java.net.SocketTimeoutException

class StockSearchRepository(token: String): BaseActionsRepository(token) {


    suspend fun getStockInfo(stockSymbol: String) : Resource<StockInfo> {


        return saveApiCall { authenticateService.getStockInfo(stockSymbol) }
//
//        try {
//
//            val response = authenticateService.getStockInfo(stockSymbol)
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401 || response.code() == 404){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }

    }

}