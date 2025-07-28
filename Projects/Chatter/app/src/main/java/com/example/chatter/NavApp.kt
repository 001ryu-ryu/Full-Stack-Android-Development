package com.example.chatter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chatter.feature.auth.signin.SignInScreen
import com.example.chatter.feature.auth.signup.SignUpScreen
import com.example.chatter.feature.chat.ChatScreen
import com.example.chatter.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

@Composable
fun NavApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser

        val start = if (currentUser != null) {
            // User is signed in, navigate to HomeScreen
            Routes.HomeScreen
        } else {
            Routes.SignUpScreen
        }

        NavHost(navController = navController, startDestination = start) {
            composable<Routes.LoginScreen> {
                SignInScreen(navController)
            }

            composable<Routes.SignUpScreen> {
                SignUpScreen(navController)
            }
            composable<Routes.HomeScreen> {
                HomeScreen(navController)
            }

            composable<Routes.ChatScreen> {
                val channelId = it.toRoute<Routes.ChatScreen>().channelId
                ChatScreen(navHostController = navController, channelId = channelId)
            }

        }
    }
}

sealed class Routes {
    @Serializable
    object LoginScreen : Routes()

    @Serializable
    object SignUpScreen : Routes()

    @Serializable
    object HomeScreen : Routes()

    @Serializable
    data class ChatScreen(val channelId: String) : Routes()
}