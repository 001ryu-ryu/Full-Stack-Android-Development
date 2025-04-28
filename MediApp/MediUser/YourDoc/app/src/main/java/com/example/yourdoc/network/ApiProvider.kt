package com.example.yourdoc.network

import com.example.yourdoc.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

    fun providerApiService() =
        Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().build()).addConverterFactory(
            GsonConverterFactory.create()).build()
}