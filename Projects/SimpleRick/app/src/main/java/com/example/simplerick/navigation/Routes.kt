package com.example.simplerick.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object CharacterDetailsScreen : Routes()

    @Serializable
    data class CharacterEpisodeScreen(val characterId: Int) : Routes()
}