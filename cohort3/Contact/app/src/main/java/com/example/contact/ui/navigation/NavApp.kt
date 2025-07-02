package com.example.contact.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contact.ui.screen.AddContactScreen
import com.example.contact.ui.screen.ContactsScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Home) {
        composable<Routes.Home> {
            ContactsScreen(navHostController = navController)
        }

        composable<Routes.AddContact> {
            AddContactScreen(navHostController = navController)
        }
    }
}