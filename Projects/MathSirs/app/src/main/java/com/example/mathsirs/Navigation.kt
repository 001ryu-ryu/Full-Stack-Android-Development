package com.example.mathsirs

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mathsirs.ui.screens.DetailScreen
import com.example.mathsirs.ui.screens.NameScreen
import com.example.mathsirs.ui.screens.ProfilePictureScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "teacher") {
        composable(
            route = "teacher"
        ) {
            ProfilePictureScreen {
                navController.navigate("detail/${it}")

            }
        }

        composable(
            route = "detail/{name}",
            arguments = listOf(
                navArgument("name") { 
                    type = NavType.StringType
                }
            )
        ) {
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            DetailScreen()
        }
    }
}