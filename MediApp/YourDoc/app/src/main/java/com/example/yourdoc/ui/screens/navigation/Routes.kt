package com.example.yourdoc.ui.screens.navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Routes {


    @Serializable
    object SignUpRoute


    @Serializable
    object LoginRoute


    @Serializable
    data class WaitingRoute(val userID: String)

    @Serializable
    object HomeScreenRoute
}