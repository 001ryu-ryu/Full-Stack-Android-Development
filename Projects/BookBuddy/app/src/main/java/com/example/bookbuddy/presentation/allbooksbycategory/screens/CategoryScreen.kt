package com.example.bookbuddy.presentation.allbooksbycategory.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookbuddy.presentation.effects.CategoryShimmer
import com.example.bookbuddy.presentation.ui.BookCategoryCard
import com.example.bookbuddy.presentation.viewmodel.BookViewModel

@Composable
fun CategoryScreen(viewmodel: BookViewModel = hiltViewModel(), navHostController: NavHostController) {
    LaunchedEffect(Unit) { 
        viewmodel.getCategories()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        val res = viewmodel.state.value
        when {
            res.isLoading -> {
                Column(Modifier.fillMaxSize()) {
                    LazyVerticalGrid(
                        GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(10) {
                            CategoryShimmer()
                        }

                    }
                }
            }

            res.error.isNotEmpty() -> {
                Text(text = res.error)
            }

            res.category.isNotEmpty() -> {
                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(res.category) {
                        BookCategoryCard(
                            imageUrl = it.categoryImageUrl,
                            category = it.categoryName,
                            navHostController = navHostController
                        )
                    }

                }
            }
        }

    }
}























