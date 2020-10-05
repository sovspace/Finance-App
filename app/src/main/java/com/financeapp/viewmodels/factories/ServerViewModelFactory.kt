package com.financeapp.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.financeapp.viewmodels.LoginViewModel
import com.financeapp.viewmodels.SignUpViewModel
import com.financeapp.viewmodels.SplashScreenViewModel
import com.financeapp.webservice.AuthenticationService
import java.lang.IllegalArgumentException

class ServerViewModelFactory(val authenticationServer: AuthenticationService): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(authenticationServer) as T
            SignUpViewModel::class.java -> SignUpViewModel(authenticationServer) as T
            SplashScreenViewModel::class.java -> SplashScreenViewModel(authenticationServer) as T
            else -> throw IllegalArgumentException("ViewModel not found")
        }
    }

}