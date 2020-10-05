package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.utils.Resource
import com.financeapp.webservice.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class SplashScreenViewModel(private val server: AuthenticationService) : ViewModel() {

    private val tokenIsValid = MutableLiveData<Resource<Boolean>>()

    fun getTokenIsValid(): LiveData<Resource<Boolean>> = tokenIsValid

    fun authentificateUser(token: String){
        viewModelScope.launch(Dispatchers.IO) {
            tokenIsValid.postValue(Resource.loading())

            val response: Response<Unit> = server.authenticateUser(token)
            if(response.isSuccessful){
                tokenIsValid.postValue(Resource.success(true))
            } else {
                tokenIsValid.postValue(Resource.error(response.message()))
            }

        }
    }


}