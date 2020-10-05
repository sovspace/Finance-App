package com.financeapp.repositories

import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCall
import com.financeapp.webservice.ActionsService
import com.financeapp.webservice.ServiceGenerator
import com.financeapp.webservice.models.LastUpdatedRequest
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*


class BalanceHistoryRepository(token: String) {

    private val authenticateService: ActionsService = ServiceGenerator.createService(ActionsService::class.java, token)


    suspend fun getBalanceHistory() : Resource<List<BalanceHistoryEntry>> {

        return saveApiCall{authenticateService.getHistory(LastUpdatedRequest(Date()))}
//
//        try {
//            val response = authenticateService.getHistory()
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
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }

    }

    suspend fun getBalanceHistoryCsv(): Resource<String>{

        return saveApiCall { authenticateService.getBalanceHistoryCsv() }
//        try {
//
//            val response = authenticateService.getBalanceHistoryCsv()
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
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