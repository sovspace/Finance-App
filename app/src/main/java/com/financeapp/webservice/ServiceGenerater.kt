package com.financeapp.webservice

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.http.HTTP


object ServiceGenerator {

    private const val API_BASE_URL = "http://192.168.43.172:8000/api/"

    private val httpClient = OkHttpClient.Builder()
    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                    .create()
            )
        )

    private var retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        if (authToken?.isNotEmpty() != null) {
            val interceptor = AuthenticationInterceptor(authToken)

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)

                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }

        return retrofit.create(serviceClass)
    }

    class AuthenticationInterceptor(private val authToken: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val original = chain.request()
            val builder = original.newBuilder()
                .header("Authorization", "Token $authToken")

            val request = builder.build()
            return chain.proceed(request)
        }
    }
}

