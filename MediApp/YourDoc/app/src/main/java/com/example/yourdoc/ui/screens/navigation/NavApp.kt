package com.example.yourdoc.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.yourdoc.ui.screens.HomeScreen
import com.example.yourdoc.ui.screens.LoginScreen
import com.example.yourdoc.ui.screens.SignUp
import com.example.yourdoc.ui.screens.WaitingScreen
import com.example.yourdoc.viewmodel.MyViewModel

@Composable
fun NavApp(viewModel: MyViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SignUpRoute
    ) {
        composable<Routes.SignUpRoute> {

            SignUp(viewModel, navController)
        }

        composable<Routes.LoginRoute> {
            LoginScreen(viewModel, navController)
        }


        composable<Routes.WaitingRoute> {
            val data = it.toRoute<Routes.WaitingRoute>()
            WaitingScreen(data.userID, viewModel, navController)
        }

        composable<Routes.HomeScreenRoute> {
            HomeScreen()
        }
    }
}