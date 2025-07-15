package com.example.bookbuddy.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.data.model.BookCategory
import com.example.bookbuddy.domain.repository.CategoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val categoriesRepository: CategoriesRepository) : ViewModel() {
    private val _catState = MutableStateFlow(CatResultState())
    val catState = _catState.asStateFlow()

    init {
        getAllCategories()
    }
    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categoriesRepository.getBooksCategories().collect {
                when(it) {
                    is ResultState.Failure -> {
                        _catState.value = _catState.value.copy(
                            error = it.error
                        )
                    }
                    is ResultState.Loading -> {
                        _catState.value = _catState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Success -> {
                        _catState.value = _catState.value.copy(
                            isLoading = false,
                            success = it.data
                        )
                    }
                }
            }
        }
    }
}

data class CatResultState(
    val isLoading: Boolean = false,
    val success: List<BookCategory> = emptyList(),
    val error: Exception? = null
)






















