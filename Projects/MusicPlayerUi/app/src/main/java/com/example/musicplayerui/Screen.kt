package com.example.musicplayerui

import androidx.annotation.DrawableRes

sealed class Screen(val title: String, val route: String) {
    
    sealed class BottomScreen(
        val bTitle: String, val bRoute: String, @DrawableRes val icon: Int) : Screen(
            bTitle, bRoute
    ) {
            object Home: BottomScreen(
                "Home", "home", R.drawable.music_and_home
            )
        object Library: BottomScreen(
            "Library", "library", R.drawable.library_music
        )

        object Browse: BottomScreen(
            "Browse", "browse", R.drawable.music_browse
        )
        }
    
    
    sealed class DrawerScreen(val dTitle: String, val dRoute: String, @DrawableRes val icon: Int) : Screen(
        dTitle, dRoute
    ) {
        object Account: DrawerScreen(
            "Account",
            "account",
            R.drawable.ic_accoount
        )

        object Subscription: DrawerScreen(
            "Subscription",
            "subscription",
            R.drawable.ic_subscription
        )

        object AddAccount: DrawerScreen(
            "Add Account",
            "add_account",
            R.drawable.ic_baseline_person_add_alt_1_24
        )
    }
}

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Browse,
    Screen.BottomScreen.Library
)

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)