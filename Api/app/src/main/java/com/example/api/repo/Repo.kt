package com.example.api.repo

import com.example.api.models.NewsModels
import com.example.api.network.ApiProvider
import retrofit2.Response

class Repo {

    suspend fun newsProvider(
        country: String,
        category: String
    ): Response<NewsModels> {
        return ApiProvider.providerApi().getTopHeadlines(country, category)
    }
}