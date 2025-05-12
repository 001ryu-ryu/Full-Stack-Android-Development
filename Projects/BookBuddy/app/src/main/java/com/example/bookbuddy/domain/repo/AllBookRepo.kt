package com.example.bookbuddy.domain.repo

import com.example.bookbuddy.common.BookCategoryModel
import com.example.bookbuddy.common.BookModel
import com.example.bookbuddy.common.ResultState
import kotlinx.coroutines.flow.Flow

interface AllBookRepo {
    
    fun getAllBooks(): Flow<ResultState<List<BookModel>>>
    
    fun getAllCategory(): Flow<ResultState<List<BookCategoryModel>>>
    
    fun getAllBooksByCategory(category: String): Flow<ResultState<List<BookModel>>>
}