package com.financeapp.repositories

import com.financeapp.models.Balance
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCall
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.SellRequest
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*


class BalanceRepository(token:String): BaseActionsRepository(token) {

    suspend fun getBalance() : Resource<Balance> {
        return saveApiCall {  authenticateService.getBalance(LastUpdatedRequest(Date()))}

//        try {
//            val response = authenticateService.getBalance()
//
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
    }

    suspend fun sellStock(stockId: Int): Resource<String>{

        return saveApiCall { authenticateService.sellStock(SellRequest(stockId)) }
//        try {
//            val request = SellRequest(stockId)
//            val response = authenticateService.sellStock(request)
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


}