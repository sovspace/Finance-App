package com.financeapp.repositories

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
        val userInfo = userDao.getUserInfo(token)
        val resource = saveApiCallResource { authenticateService.getUserInfo(LastUpdatedRequest(userInfo.lastUpdated)) }
        val resultResource: Resource<User>

        if (resource.status == Resource.Status.OK) {
            resultResource = resource
            userDao.updateUser(UserEntity.getEntity(token, resource.getData() as User))
        } else {
            resultResource = Resource.success(User.getFromEntity(userInfo))
        }

        return resultResource
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