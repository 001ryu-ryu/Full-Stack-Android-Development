package com.example.bookbuddy.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.bookbuddy.components.BookItem
import com.example.bookbuddy.components.BooksList
import com.example.bookbuddy.components.CategoriesTab
import com.example.bookbuddy.ui.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun TabScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    val tabs = listOf(
        TabItem(
            title = "Categories",
            icon = Icons.Outlined.Category,
            filledIcon = Icons.Filled.Category
        ),
        TabItem(
            title = "Books",
            icon = Icons.Outlined.Book,
            filledIcon = Icons.Filled.Book
        )
    )

    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    modifier = Modifier.fillMaxWidth(),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {
                    if (pagerState.currentPage == index) {
                        Icon(
                            imageVector = tab.filledIcon,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = null
                        )
                    }

                    Text(tab.title)
                }
            }
        }

        HorizontalPager(
            state = pagerState
        ) {page ->
            when(page) {
                0 -> CategoriesTab {navHostController.navigate(Routes.CategoriesOfBookScreen(it))}
                1 -> BooksList() {navHostController.navigate(Routes.PdfScreen(it))}
            }
        }
    }
}

data class TabItem(
    val title: String,
    val icon: ImageVector,
    val filledIcon: ImageVector
)