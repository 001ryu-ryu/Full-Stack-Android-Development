package com.example.yourdoc.network

import com.example.yourdoc.response.CreateUserResponse
import com.example.yourdoc.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


//Here we define our Api Endpoints
interface ApiServices {
    
    @FormUrlEncoded
    @POST("createUser")
    suspend fun createUser(
        @Field("name") name : String,
        @Field("password") password : String,
        @Field("phoneNumber") phoneNumber : String,
        @Field("email") email : String,
        @Field("pinCode") pinCode : String,
        @Field("address") address : String

    ) : Response<CreateUserResponse>

}