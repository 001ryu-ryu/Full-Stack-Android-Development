package com.example.contact.ui.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home

    @Serializable
    object AddContact
}