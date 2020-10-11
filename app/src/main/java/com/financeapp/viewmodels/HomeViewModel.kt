package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.models.Balance
import com.financeapp.repositories.BalanceRepository
import com.financeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(token: String) : ViewModel() {
    var authenticatedRepository: BalanceRepository = BalanceRepository(token)

    private val balance: MutableLiveData<Resource<Balance>> = MutableLiveData()
    fun getBalance(): LiveData<Resource<Balance>> = balance

    fun requestBalance() {
        this.viewModelScope.launch(Dispatchers.IO) {
            balance.postValue(Resource.loading())
            balance.postValue(authenticatedRepository.getBalance())
        }
    }

    fun sellStock(stockId: Int) {
        this.viewModelScope.launch(Dispatchers.IO) {
            balance.postValue(Resource.loading())
            val response = authenticatedRepository.sellStock(stockId)

            if (response.status == Resource.Status.OK) {
                balance.postValue(authenticatedRepository.getBalance())
            }

        }
    }


}