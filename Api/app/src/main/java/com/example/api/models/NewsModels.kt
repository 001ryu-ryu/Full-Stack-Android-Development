package com.example.api.models

data class NewsModels(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)