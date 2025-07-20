package com.example.bookbuddy.ui.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookbuddy.components.BookItem
import com.example.bookbuddy.components.NavBackIcon
import com.example.bookbuddy.ui.navigation.Routes
import com.example.bookbuddy.ui.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksOfCategoriesScreen(
    booksOfCategory: String,
    bookViewModel: BookViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val booksState = bookViewModel.booksState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(booksOfCategory) },
                navigationIcon = { NavBackIcon() { navHostController.popBackStack() } })
        },
    ) { innerPadding ->
        booksState.value?.let { state ->
            when {
                state.success.isNotEmpty() -> {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(state.success.filter { it.bookCategory == booksOfCategory }) { book ->
                            BookItem(
                                bookName = book.bookName,
                                bookImage = book.bookImage,
                                bookAuthor = book.bookAuthor
                            ) {
                                navHostController.navigate(Routes.PdfScreen(book.bookUrl))
                            }
                        }
                    }
                }
            }
        }
    }
}





























