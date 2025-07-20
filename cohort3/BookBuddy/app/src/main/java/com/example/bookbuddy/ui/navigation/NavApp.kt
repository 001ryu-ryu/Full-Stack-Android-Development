package com.example.bookbuddy.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bookbuddy.components.PdfScreen
import com.example.bookbuddy.ui.presentation.screens.BooksOfCategoriesScreen
import com.example.bookbuddy.ui.presentation.screens.TabScreen

@Composable
fun NavApp(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HomeScreen) {
        composable<Routes.HomeScreen> {
            TabScreen(modifier = modifier, navHostController = navController)
        }

        composable<Routes.CategoriesOfBookScreen> {
            val categoryName = it.toRoute<Routes.CategoriesOfBookScreen>().bookType
            BooksOfCategoriesScreen(booksOfCategory = categoryName, navHostController = navController)
        }

        composable<Routes.PdfScreen> { it: NavBackStackEntry ->
            val url = it.toRoute<Routes.PdfScreen>().url
            Log.d("Tag", url)
            PdfScreen(url)
        }
    }
}