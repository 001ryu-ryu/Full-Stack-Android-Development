package com.example.bookbuddy.domain.repository

import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getAllTasks(): Flow<ResultState<List<Book>>>
}