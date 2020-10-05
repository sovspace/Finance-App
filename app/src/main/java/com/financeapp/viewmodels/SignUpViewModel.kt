package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.utils.Resource
import com.financeapp.webservice.AuthenticationService
import com.financeapp.webservice.models.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

class SignUpViewModel(private var server: AuthenticationService) : ViewModel() {

    var login = String()
    var password = String()
    var email = String()
    var firstName = String()
    var lastName = String()

    private val isSignedUp: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    fun getIsSignedUp(): LiveData<Resource<Boolean>> = isSignedUp

    fun onSignUpClick() {
        if (login.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {

            this.viewModelScope.launch(Dispatchers.IO) {
                try {
                    isSignedUp.postValue(Resource.loading())
                } catch (e: ConnectException) {
                    isSignedUp.postValue(Resource.error("No internet connection"))
                } catch (e: SocketTimeoutException) {
                    isSignedUp.postValue(Resource.error("No internet connection"))
                }

                val response =
                    server.signUp(SignUpRequest(login, password, email, firstName, lastName))

                if (response.isSuccessful) {
                    isSignedUp.postValue(Resource.success(true))
                } else {
                    isSignedUp.postValue(Resource.error(response.message()))
                }
            }
        } else {
            isSignedUp.value = Resource.error("Empty args")
        }
    }
}