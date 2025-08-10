package com.example.paging3.screens.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.paging3.navigation.Routes
import com.example.paging3.screens.common.ListContent

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val allImages = viewModel.getAllImages.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            HomeTopBar { navController.navigate(Routes.Search) }
        }
    ) {innerPadding ->
        ListContent(
            items = allImages
        )
    }
}