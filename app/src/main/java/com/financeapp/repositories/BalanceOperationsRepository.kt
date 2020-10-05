package com.financeapp.repositories

import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCall
import com.financeapp.webservice.models.BuyRequest
import com.financeapp.webservice.models.RefillRequest
import java.net.ConnectException
import java.net.SocketTimeoutException

class BalanceOperationsRepository(token: String) : BaseActionsRepository(token) {

    suspend fun buyStock(amount: Double, stockSymbol: String): Resource<String> {

        return saveApiCall { authenticateService.buyStock(BuyRequest(stockSymbol, amount)) }

//        try {
//            val request = BuyRequest(stockSymbol, amount)
//            val response = authenticateService.buyStock(request)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if (response.code() == 401)
//                return Resource.error(response.message())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
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

    suspend fun refillStock(amount: Double): Resource<String>{

        return saveApiCall { authenticateService.refill(RefillRequest(amount)) }
//
//        try {
//            val request = RefillRequest(amount)
//            val response = authenticateService.refill(request)
//
//
//            if (response.code() == 401)
//                return Resource.error(response.message())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//            if (response.code() == 404)
//                return Resource.error(response.body())
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