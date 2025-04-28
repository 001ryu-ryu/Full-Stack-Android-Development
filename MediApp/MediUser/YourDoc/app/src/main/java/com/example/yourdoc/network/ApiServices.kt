package com.example.yourdoc.network

import com.example.yourdoc.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


//Here we define our Api Endpoints
interface ApiServices {
    
    @FormUrlEncoded
    @POST("createUser")

}