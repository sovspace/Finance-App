package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.utils.Resource
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.models.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException


class LoginViewModel(private val server: AuthenticationService) : ViewModel() {
    var login = String()
    var password = String()

    private val token: MutableLiveData<Resource<String>> = MutableLiveData()
    fun getToken(): LiveData<Resource<String>> = token


    fun login() {
        if (login.isNotEmpty() && password.isNotEmpty()) {

            this.viewModelScope.launch(Dispatchers.IO) {

                try {
                    token.postValue(Resource.loading())
                } catch (e: ConnectException) {
                    token.postValue(Resource.error("No internet connection"))
                } catch (e: SocketTimeoutException) {
                    token.postValue(Resource.error("No internet connection"))
                }

                val response = server.logIn(LoginRequest(login, password))

                if (response.isSuccessful) {
                    token.postValue(Resource.success(response.body()!!.token))
                } else {
                    token.postValue(Resource.error(response.message()))
                }

            }
        } else {
            token.value = Resource.error("Input error")
        }
    }
}