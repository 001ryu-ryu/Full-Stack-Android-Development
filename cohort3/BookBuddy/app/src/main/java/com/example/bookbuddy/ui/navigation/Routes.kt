package com.example.bookbuddy.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    object HomeScreen: Routes()

    @Serializable
    data class CategoriesOfBookScreen(val bookType: String): Routes()

    @Serializable
    data class PdfScreen(val url: String): Routes()
}