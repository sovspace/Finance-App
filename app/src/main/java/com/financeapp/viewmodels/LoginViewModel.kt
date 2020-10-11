package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.models.LoginRequest
import com.financeapp.webservice.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException


class LoginViewModel(private val server: AuthenticationService) : ViewModel() {
    var login = String()
    var password = String()

    private val token: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    fun getToken(): LiveData<Resource<LoginResponse>> = token

    fun login() {
        if (login.isNotEmpty() && password.isNotEmpty()) {

            this.viewModelScope.launch(Dispatchers.IO) {

                token.postValue(Resource.loading())
//                try {
//                } catch (e: ConnectException) {
//                    token.postValue(Resource.error("No internet connection"))
//                } catch (e: SocketTimeoutException) {
//                    token.postValue(Resource.error("No internet connection"))
//                }

                token.postValue(saveApiCallResource {  server.logIn(LoginRequest(login, password))})

            }
        } else {
            token.value = Resource.error("Input error")
        }
    }
}