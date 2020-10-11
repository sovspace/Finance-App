package com.financeapp.repositories

import com.financeapp.models.User
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.PasswordRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepository(token: String) : BaseActionsRepository(token) {

    suspend fun getUserInfo(): Resource<User> {
        return saveApiCallResource { authenticateService.getUserInfo(LastUpdatedRequest(null)) }
    }

    suspend fun changePassword(currentPassword: String, newPassword: String): Resource<String> {

        return saveApiCallResource {
            authenticateService.changePassword(
                PasswordRequest(
                    currentPassword,
                    newPassword
                )
            )
        }
    }

    suspend fun changeAvatar(avatarUri: String): Resource<String> {
        val file = File(avatarUri)
        val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        return saveApiCallResource { authenticateService.changeAvatar(body) }
    }
}