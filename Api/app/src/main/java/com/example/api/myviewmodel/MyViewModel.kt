package com.example.api.myviewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.models.NewsModels
import com.example.api.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    val response = mutableStateOf<NewsModels?>(null)

    val repo = Repo()
    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.newsProvider(
                country = "us",
                category = "business"
            )

            response.value = data.body()
        }
    }
}