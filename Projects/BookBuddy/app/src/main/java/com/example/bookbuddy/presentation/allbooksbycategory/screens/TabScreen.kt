package com.example.bookbuddy.presentation.allbooksbycategory.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

data class TabItem(
    val title: String,
    val icon: ImageVector
)


@Composable
fun TabScreen(navHostController: NavHostController) {
    val tabs = listOf<TabItem>(
        TabItem("Category", Icons.Default.Category),
        TabItem("Books", Icons.Default.Book)
    )

    val pagerState = rememberPagerState(pageCount = {tabs.size})
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            // This ensures if user swipes pager, tab selection updates.
            // No need to animateScrollToPage here as pagerState is the source of truth.
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed {
                index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch { 
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(tab.title)
                    },
                    icon = { // Using the dedicated icon parameter
                        Icon(imageVector = tab.icon, contentDescription = tab.title)
                    }
                    )
                }
            }
        Box(modifier = Modifier.weight(1f)) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                when (page) {
                    0 -> CategoryScreen(navHostController = navHostController)
                    1 -> AllBooksScreen(
                        modifier = Modifier.fillMaxSize(),
                        navHostController = navHostController
                    )
                }
            }
        }
        }

    }
