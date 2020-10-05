package com.financeapp.repositories

import com.financeapp.models.User
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCall
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.PasswordRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*

class UserRepository(token: String) : BaseActionsRepository(token) {

    suspend fun getUserInfo(): Resource<User> {

        return saveApiCall { authenticateService.getUserInfo(LastUpdatedRequest(Date())) }
//
//        try {
//            val response = authenticateService.getUserInfo()
//
//
//            if(response.code() == 200){
//                return Resource.success(response.body())
//            }
//            if(response.code() == 401 || response.code() == 404){
//                return Resource.error(response.message())
//            }
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }

    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Resource<String> {

        return saveApiCall {
            authenticateService.changePassword(
                PasswordRequest(
                    currentPassword,
                    newPassword
                )
            )
        }
//        try {
//            val request = PasswordRequest(currentPassword, newPassword)
//            val response = authenticateService.changePassword(request)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
    }

    suspend fun changeAvatar(avatarUri: String): Resource<String> {
        val file = File(avatarUri)
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        return saveApiCall { authenticateService.changeAvatar(body) }

//        try {
//
//
//            val file = File(avatarUri)
//            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
//            val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
//            val response = authenticateService.changeAvatar(body)
//
//            if (response.code() == 404)
//                return Resource.error(response.body())
//
//            if(response.code() == 200)
//                return Resource.success(response.body())
//
//            return Resource.error(response.message())
//        }
//        catch (e: ConnectException){
//            return Resource.error("No internet connection")
//        }
//        catch (e: SocketTimeoutException){
//            return Resource.error("No internet connection")
//        }
//    }
    }
}