package com.example.musicplayerui

import androidx.annotation.DrawableRes

sealed class Screen(val title: String, val route: String) {
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

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)