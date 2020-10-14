package com.financeapp.repositories

import com.financeapp.database.dao.BalanceDao
import com.financeapp.database.entities.BalanceHistoryEntryEntity
import com.financeapp.models.BalanceHistoryEntry
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.ActionsService
import com.financeapp.webservice.ServiceGenerator
import com.financeapp.webservice.models.LastUpdatedRequest

class BalanceHistoryRepository(private val token: String, private val balanceDao: BalanceDao) {

    private val authenticateService: ActionsService =
        ServiceGenerator.createService(ActionsService::class.java, token)

    suspend fun getBalanceHistory(): Resource<List<BalanceHistoryEntry>> {
        val balanceAndHistory = balanceDao.getBalanceHistory(token)

        val request = if (balanceAndHistory?.history == null || balanceAndHistory.history.isEmpty()) {
            LastUpdatedRequest(null)
        } else {
            LastUpdatedRequest(balanceAndHistory.balance.lastUpdated)
        }

        val webResource = saveApiCallResource {
            authenticateService.getHistory(request)
        }

        when (webResource.status) {
            Resource.Status.OK -> {
                return if (webResource.getData() == null) {
                    Resource.success(BalanceHistoryEntry.getFromEntities(balanceAndHistory?.history as List<BalanceHistoryEntryEntity>))
                } else {
                    balanceDao.insertBalanceHistory(
                        BalanceHistoryEntryEntity.getEntities(
                            token,
                            webResource.getData() as List<BalanceHistoryEntry>
                        )
                    )
                    Resource.success(webResource.getData())
                }

            }
            Resource.Status.ERROR -> {
                return if (balanceAndHistory == null) {
                    Resource.error("No information available")
                } else {
                    val balanceHistory = ArrayList<BalanceHistoryEntry>()
                    for (balanceHistoryEntryEntity in balanceAndHistory.history) {
                        balanceHistory.add(
                            BalanceHistoryEntry.getFromEntity(
                                balanceHistoryEntryEntity
                            )
                        )
                    }
                    Resource.success(balanceHistory)
                }
            }
            Resource.Status.LOADING -> return Resource.loading()
        }


    }

    suspend fun getBalanceHistoryCsv(): Resource<String> {
        return saveApiCallResource { authenticateService.getBalanceHistoryCsv() }
    }

}