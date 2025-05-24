package com.example.bookbuddy.presentation.allbooksbycategory.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.bookbuddy.presentation.allbooksbycategory.screens.AllBooksByCategoryScreen
import com.example.bookbuddy.presentation.allbooksbycategory.screens.HomeScreen
import com.example.bookbuddy.presentation.ui.PdfViewer

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = Routes.HomeScreen) { 
        composable<Routes.HomeScreen> {
            HomeScreen(navHostController)
        }

        composable<Routes.ShowPdfScreen> {
            backStackEntry ->
            val data: Routes.ShowPdfScreen = backStackEntry.toRoute()
            PdfViewer(url = data.url)
        }

        composable<Routes.BooksByCategory> {
            backStackEntry ->
            val data : Routes.BooksByCategory = backStackEntry.toRoute()
            AllBooksByCategoryScreen(category = data.category, navHostController = navHostController)
        }

        
    }
}