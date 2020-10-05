package com.financeapp.utils

import retrofit2.HttpException
import retrofit2.Response

suspend fun <T: Any> saveApiCall(call: suspend() -> Response<T>): Resource<T>{
    return try {
        Resource.success(call.invoke().body())
    } catch (he: HttpException) {
        Resource.error(he.message())
    }
}