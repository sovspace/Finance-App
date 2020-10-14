package com.financeapp.utils

import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

suspend fun <T: Any> saveApiCallResource(call: suspend() -> Response<T>): Resource<T>{
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(response.message())
        }
    }
    catch (e: ConnectException) {
        Resource.error("Enable to connect to server")
    }
    catch (he: HttpException) {
        Resource.error(he.message)
    }
}
