package com.example.musicplayerui

import androidx.annotation.DrawableRes

data class Lib(@DrawableRes val icon: Int, val name: String)

val libraries = listOf<Lib>(
    Lib(R.drawable.ic_subscription, "Playlist"),
    Lib(R.drawable.ic_subscription, "Artists"),
    Lib(R.drawable.ic_subscription, "Album"),
    Lib(R.drawable.ic_subscription, "Songs"),
    Lib(R.drawable.ic_subscription, "Genre")
)