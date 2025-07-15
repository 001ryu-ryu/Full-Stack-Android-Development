package com.example.bookbuddy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.Book
import com.example.bookbuddy.domain.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val bookRepository: BookRepository) : ViewModel() {
    private val _booksState = MutableStateFlow<BookResultState?>(null)
    val booksState = _booksState.asStateFlow()

    init {
        getAllBooks()
    }
    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            bookRepository.getAllTasks().collect {
                when(it) {
                    is ResultState.Failure -> {
                        _booksState.value = BookResultState(
                            isLoading = false,
                            success = emptyList(),
                            error = it.error
                        )
                    }
                    is ResultState.Loading -> {
                        _booksState.value = BookResultState(
                            isLoading = true,
                            success = emptyList(),
                            error = null
                        )
                    }
                    is ResultState.Success -> {
                        _booksState.value = BookResultState(
                            isLoading = false,
                            success = it.data,
                            error = null
                        )
                    }
                }
            }
        }
    }
}

data class BookResultState(
    val isLoading: Boolean = false,
    val success: List<Book> = emptyList(),
    val error: Exception? = null
)


























