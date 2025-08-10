package com.example.paging3.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home: Routes()
    @Serializable
    object Search: Routes()
}