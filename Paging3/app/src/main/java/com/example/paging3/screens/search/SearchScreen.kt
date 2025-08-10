package com.example.paging3.screens.search

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.paging3.screens.common.ListContent

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery
    val  searchedImages = viewModel.searchedImages.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            SearchWidget(
                text = searchQuery,
                onTextChange = { viewModel.updateSearchQuery(query = it) },
                onSearchClicked = {viewModel.searchHeroes(it)},
                onCloseClick = {
                    navController.popBackStack()
                }
            )
        }
    ) {innerPadding ->
        ListContent(items = searchedImages)
    }
}