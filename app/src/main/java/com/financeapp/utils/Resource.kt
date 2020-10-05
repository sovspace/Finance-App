package com.financeapp.utils

class Resource<T>(
    val status: Status,
    private val data: T?,
    private val message: String?
) {
    var isHandled = false

    fun getData(): T? {
        isHandled = true
        return data
    }

    fun getMessage(): String? {
        isHandled = true
        return message
    }


    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.OK, data, null)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }

        fun <T> error(message: String?): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }

    }

    enum class Status { OK, ERROR, LOADING }
}