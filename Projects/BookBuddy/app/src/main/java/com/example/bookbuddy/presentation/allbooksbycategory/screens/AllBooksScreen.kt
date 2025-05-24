package com.example.bookbuddy.presentation.allbooksbycategory.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookbuddy.presentation.effects.AnimatedShimmer
import com.example.bookbuddy.presentation.ui.Bookcart
import com.example.bookbuddy.presentation.viewmodel.BookViewModel

@Composable
fun AllBooksScreen(
    modifier: Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navHostController: NavHostController) {

    LaunchedEffect(Unit) { 
        viewModel.getAllBooks()
    }

    val res = viewModel.state.value

    when {
        res.isLoading -> {
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn { 
                    items(10) {
                        AnimatedShimmer()
                    }
                }

            }
        }

        res.error.isNotEmpty() -> {
            Text(text = res.error)

        }

        res.items.isNotEmpty() -> {
            Column(Modifier.fillMaxSize()) {

                LazyColumn(Modifier.fillMaxSize()) {
                    items(res.items) {
                        Bookcart(
                            imageUrl = it.image,
                            title = it.bookName,
                            author = it.bookAuthor,
                            des = it.bookDescription,
                            navHostController = navHostController,
                            bookUrl = it.bookUrl
                        )
                    }

                }
            }
        }

        else -> {
            Text("No Books Available Right Now")
        }
    }
}


































