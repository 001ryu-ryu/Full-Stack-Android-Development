package com.example.bookbuddy.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookbuddy.common.BookCategoryModel
import com.example.bookbuddy.common.BookModel
import com.example.bookbuddy.common.ResultState
import com.example.bookbuddy.domain.repo.AllBookRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(private val repository: AllBookRepo) : ViewModel() {
    
    private val _state: MutableState<ItemState> = mutableStateOf(ItemState())

    val state: State<ItemState>
        get() = _state

    fun getAllBooks() {
        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllBooks().collect { 
                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage!!)
                    }
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {

            repository.getAllCategory().collect { 
                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage!!)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(category = it.data)
                    }
                }
            }
        }
    }

    fun getBooksByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllBooksByCategory(category).collect {
                
                when(it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage!!)
                    }
                }

            }
        }

    }

}

// Now we need to make the data class to handle the state of the UI inside our view model
data class ItemState(
    val isLoading: Boolean = false,
    val items: List<BookModel> = emptyList(),
    val error: String ="",
    val category: List<BookCategoryModel> = emptyList()
)