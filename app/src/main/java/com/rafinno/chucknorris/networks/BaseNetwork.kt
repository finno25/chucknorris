package com.rafinno.chucknorris.networks

import com.google.gson.GsonBuilder
import com.rafinno.chucknorris.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BaseNetwork {
    private fun buildService(serverBaseUrl: String): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(serverBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    private val okHttpClient: OkHttpClient
        private get() {
            val httpClient = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                httpClient.addInterceptor(createLogginInterceptor())
            }
            return httpClient.build()
        }

    private fun createLogginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    val chuckNorrisApi: ApiInterface
        get() = buildService(BASE_URL_CHUCK_NORRIS).create(
            ApiInterface::class.java
        )

    companion object {
        private const val BASE_URL_CHUCK_NORRIS = "https://api.chucknorris.io/"
        private var INSTANCE: BaseNetwork = BaseNetwork()
        val instance: BaseNetwork
            get() {
                return INSTANCE
            }
    }
}