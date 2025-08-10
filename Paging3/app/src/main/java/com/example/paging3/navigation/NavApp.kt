package com.example.paging3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.paging3.screens.home.HomeScreen
import com.example.paging3.screens.search.SearchScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home) {
        composable<Routes.Home> {
            HomeScreen(navController = navController)
        }

        composable<Routes.Search> {
            SearchScreen(navController = navController)
        }

    }
}