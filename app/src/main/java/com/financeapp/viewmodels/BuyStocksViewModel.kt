package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.repositories.AuthenticatedRepository
import com.financeapp.repositories.BalanceOperationsRepository
import com.financeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyStocksViewModel(token: String) : ViewModel() {

    private var authenticatedRepository: BalanceOperationsRepository = BalanceOperationsRepository(token)

    var stockName = String()
    var stockAmount = String()
    var refillAmount = String()

    private val response: MutableLiveData<Resource<String>> = MutableLiveData()
    fun getResponse(): LiveData<Resource<String>> = response

    fun restoreValues() {
        stockName = ""
        stockAmount = ""
        refillAmount = ""
    }

    fun buyStock() {
        this.viewModelScope.launch(Dispatchers.IO) {
            response.postValue(Resource.loading())
            if (stockAmount.toDoubleOrNull() != null) {
                response.postValue(
                    authenticatedRepository.buyStock(stockAmount.toDouble(), stockName)
                )
            } else {
                response.postValue(Resource.error("Bad representation"))
            }
            restoreValues()
        }
    }

    fun refill() {
        this.viewModelScope.launch(Dispatchers.IO) {
            response.postValue(Resource.loading())
            if (refillAmount.toDoubleOrNull() != null) {
                response.postValue(
                    authenticatedRepository.refillStock(refillAmount.toDouble())
                )
            } else {
                response.postValue(Resource.error("Bad representation"))
            }
            restoreValues()
        }

    }
}