package com.example.newsapp.service

import com.example.newsapp.constants.API_KEY
import com.example.newsapp.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=d6fb9d63179c40e9a89e540e341e3dd7

    @GET("top-headlines")
    suspend fun apiCall(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = API_KEY
    ) : Response<NewsModel>
}