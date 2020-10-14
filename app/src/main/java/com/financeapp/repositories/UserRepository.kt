package com.financeapp.repositories

import android.util.Log
import com.financeapp.database.dao.UserDao
import com.financeapp.database.entities.UserEntity
import com.financeapp.models.User
import com.financeapp.utils.Resource
import com.financeapp.utils.saveApiCallResource
import com.financeapp.webservice.models.LastUpdatedRequest
import com.financeapp.webservice.models.PasswordRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UserRepository(private val token: String, private val userDao: UserDao) :
    BaseActionsRepository(token) {

    suspend fun getUserInfo(): Resource<User> {
        val userEntity = userDao.getUserInfo(token)
        val webServiceResource =
            saveApiCallResource { authenticateService.getUserInfo(LastUpdatedRequest(userEntity?.lastUpdated)) }

        return when (webServiceResource.status) {
            Resource.Status.OK -> {
                if (webServiceResource.getData() == null) {
                    Resource.success(User.getFromEntity(userEntity as UserEntity))
                } else {
                    userDao.insertUser(UserEntity.getEntity(token, webServiceResource.getData() as User))
                    Resource.success(webServiceResource.getData())
                }
            }
            Resource.Status.ERROR -> {
                if (userEntity == null) {
                    Resource.error("No information available")
                } else {
                    Resource.success(User.getFromEntity(userEntity))
                }
            }
            Resource.Status.LOADING -> Resource.loading()
        }
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