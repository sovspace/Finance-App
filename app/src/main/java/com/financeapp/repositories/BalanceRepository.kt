package com.financeapp.repositories

import android.util.Log
import com.financeapp.database.dao.BalanceDao
import com.financeapp.database.entities.BalanceEntity
import com.financeapp.database.entities.BalanceStockEntity
import com.financeapp.models.Balance
import com.financeapp.models.BalanceStock
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.SellRequest


class BalanceRepository(private val token: String, private val balanceDao: BalanceDao): BaseActionsRepository(token) {

    suspend fun getBalance() : Resource<Balance> {
        val balanceAndStocks = balanceDao.getBalanceStocks(token)
        val webResource =  saveApiCallResource {  authenticateService.getBalance(LastUpdatedRequest(balanceAndStocks?.balance?.lastUpdated))}

        when(webResource.status) {
            Resource.Status.OK -> {
                return if (webResource.getData() == null) {
                    Resource.success(Balance.getFromEntity(balanceAndStocks!!.balance, balanceAndStocks.stocks))
                } else {
                    val balanceStocksEntities = ArrayList<BalanceStockEntity>()
                    for (balanceStock in webResource.getData()?.stocks as List<BalanceStock>){
                        balanceStocksEntities.add(BalanceStockEntity.getEntity(token, balanceStock))
                    }
                    balanceDao.insertBalanceAndBalanceStocks(BalanceEntity.getEntity(token, webResource.getData() as Balance), balanceStocksEntities)

                    Resource.success(webResource.getData())
                }
            }
            Resource.Status.ERROR -> {
                return if (balanceAndStocks == null) {
                    Resource.error("No information available")
                } else {
                    Resource.success(Balance.getFromEntity(balanceAndStocks.balance, balanceAndStocks.stocks))
                }
            }
            Resource.Status.LOADING -> return Resource.loading()
        }
    }

    suspend fun sellStock(stockId: Int): Resource<String>{

        val webResource = saveApiCallResource { authenticateService.sellStock(SellRequest(stockId)) }
        if (webResource.status == Resource.Status.OK) {
            balanceDao.deleteBalanceStock(stockId)
        }

        return webResource
    }


}