package com.example.bookbuddy.presentation.allbooksbycategory.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.bookbuddy.presentation.effects.AnimatedShimmer
import com.example.bookbuddy.presentation.ui.Bookcart
import com.example.bookbuddy.presentation.viewmodel.BookViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBooksByCategoryScreen(
    category: String,
    navHostController: NavHostController,
    viewModel: BookViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getBooksByCategory(category)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {Text(category)},
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icons.AutoMirrored.Default.ArrowBack
                    }
                }
            )
        }
    ) {
        
        val res = viewModel.state.value

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
        ) {
            when {
                res.isLoading -> {
                    Column(Modifier.fillMaxSize()
                        ) {
                        LazyColumn { 
                            items(10) {
                                AnimatedShimmer()
                            }
                        }

                    }
                }

                res.error.isNotEmpty() -> {
                    Text(res.error)
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

    }

}


























