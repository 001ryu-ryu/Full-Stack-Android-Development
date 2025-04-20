package com.example.api.network

import com.example.api.models.NewsModels
import com.example.api.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsModels>
}