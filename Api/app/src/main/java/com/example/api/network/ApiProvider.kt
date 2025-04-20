package com.example.api.network

import com.example.api.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    val okHttpClient = OkHttpClient.Builder()
    fun providerApi() = Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient.build()).addConverterFactory(
        GsonConverterFactory.create()).build().create(ApiServices::class.java)
}