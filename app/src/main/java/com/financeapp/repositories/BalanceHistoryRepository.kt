package com.financeapp.repositories

import com.financeapp.database.dao.BalanceDao
import com.financeapp.database.dao.UserDao
import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.ActionsService
import com.financeapp.webservice.ServiceGenerator
import com.financeapp.webservice.models.LastUpdatedRequest

class BalanceHistoryRepository(token: String, private val userDao: UserDao) {

    private val authenticateService: ActionsService = ServiceGenerator.createService(ActionsService::class.java, token)

    suspend fun getBalanceHistory() : Resource<List<BalanceHistoryEntry>> {
        return saveApiCallResource{authenticateService.getHistory(LastUpdatedRequest(null))}
    }

    suspend fun getBalanceHistoryCsv(): Resource<String>{
        return saveApiCallResource { authenticateService.getBalanceHistoryCsv() }
    }

}