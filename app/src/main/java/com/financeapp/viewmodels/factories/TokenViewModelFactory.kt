package com.financeapp.viewmodels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.financeapp.viewmodels.*
import java.lang.IllegalArgumentException

class TokenViewModelFactory(val token: String, val context:Context? = null) : ViewModelProvider.Factory{


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(token) as T
            BalanceHistoryViewModel::class.java -> BalanceHistoryViewModel(token, context as Context) as T
            BuyStocksViewModel::class.java -> BuyStocksViewModel(token) as T
            SettingsViewModel::class.java -> SettingsViewModel(token) as T
            StocksViewModel::class.java -> StocksViewModel(token) as T
            else -> throw IllegalArgumentException("ViewModel not found")
        }

    }
}