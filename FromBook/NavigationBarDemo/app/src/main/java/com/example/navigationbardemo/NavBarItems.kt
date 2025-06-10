package com.example.navigationbardemo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = NavRoutes.Home.route
        ),
        BarItem(
            title = "Contacts",
            image = Icons.Filled.Face,
            route = NavRoutes.Contacts.route
        ),
         BarItem(
            title = "Favorites",
            image = Icons.Filled.Favorite,
            route = NavRoutes.Favorites.route
        )


    )
}