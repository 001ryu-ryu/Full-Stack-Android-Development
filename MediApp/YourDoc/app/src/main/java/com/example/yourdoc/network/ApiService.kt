package com.example.yourdoc.network

import com.example.yourdoc.network.response.CreateUserResponse
import com.example.yourdoc.network.response.LogInUserResponse
import com.example.yourdoc.network.response.UserResponseById
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("createUser")
    suspend fun createUser(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("pinCode") pinCode: String,
        @Field("address") address: String,
        @Field("email") email: String
    ): Response<CreateUserResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun logInUser(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LogInUserResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getUserById(
        @Field("user_id") userId: String
    ): Response<UserResponseById>
}