package com.financeapp.utils

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

suspend fun <T: Any> saveApiCallResource(call: suspend() -> Response<T>): Resource<T>{
    try {
        val response = call.invoke()
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            val message: String = when(response.code()) {
                404 -> "Page not found"
                401 -> "Unauthorized"
                else -> response.message()
            }
            Resource.error(message)
        }
    }
    catch (e: ConnectException) {
        return Resource.error("Enable to connect to server")
    }
    catch (he: HttpException) {
        return Resource.error(he.message)
    }
}
