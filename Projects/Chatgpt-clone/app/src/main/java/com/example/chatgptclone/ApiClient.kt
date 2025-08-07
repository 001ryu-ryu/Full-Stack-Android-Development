package com.example.chatgptclone

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    @Volatile
    private var INSTANCE: ApiInterface? = null
    fun getInstance(): ApiInterface {
        synchronized(this) {
            return INSTANCE ?: Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(Interceptor { chain ->
                            val request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer $API_KEY")
                                .addHeader("Content-Type", "application/json")
                                .build()
                            chain.proceed(request)
                        })
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
                .also {
                    INSTANCE = it
                }
        }
    }

}