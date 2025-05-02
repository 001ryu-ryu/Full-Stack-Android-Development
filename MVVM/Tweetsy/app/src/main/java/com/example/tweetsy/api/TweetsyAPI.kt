package com.example.tweetsy.api

import com.example.tweetsy.models.TweetListItem
import com.example.tweetsy.utils.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetsyAPI {

    @GET(ENDPOINT)
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<TweetListItem>>

    @GET(ENDPOINT)
    @Headers("X-JSON-Path: tweets..category")
    suspend fun getCategories(): Response<List<String>>
}