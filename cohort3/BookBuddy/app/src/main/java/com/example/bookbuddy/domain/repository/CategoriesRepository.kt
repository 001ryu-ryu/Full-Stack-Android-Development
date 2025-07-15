package com.example.bookbuddy.domain.repository

import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.BookCategory
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getBooksCategories(): Flow<ResultState<List<BookCategory>>>
}