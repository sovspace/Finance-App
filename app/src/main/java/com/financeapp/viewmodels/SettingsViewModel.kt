package com.financeapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.models.User
import com.financeapp.repositories.UserRepository
import com.financeapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingsViewModel(token: String) : ViewModel() {

    private val authenticatedRepository: UserRepository = UserRepository(token)

    var avatar = String()
    var currentPassword = String()
    var newPassword =  String()

    private val user: MutableLiveData<Resource<User>> = MutableLiveData()
    fun getUser(): LiveData<Resource<User>> = user

    fun requestUser(){
        this.viewModelScope.launch(Dispatchers.IO) {
            user.postValue(Resource.loading())
            user.postValue(authenticatedRepository.getUserInfo())
        }
    }

    fun changeAvatar(){
        this.viewModelScope.launch(Dispatchers.IO){
            if(avatar.isNotEmpty()){
                authenticatedRepository.changeAvatar(avatar)
            }
            requestUser()
        }

    }

    fun changePassword(){
        this.viewModelScope.launch(Dispatchers.IO){
            if(currentPassword.isNotEmpty()  && newPassword.isNotEmpty()){
                authenticatedRepository.changePassword(currentPassword, newPassword)
            }
        }
    }
}