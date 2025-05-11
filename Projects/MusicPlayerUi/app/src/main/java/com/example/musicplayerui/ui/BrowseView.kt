package com.example.musicplayerui.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import com.example.musicplayerui.R

@Composable
fun Browse() {
    val categories = listOf("Hits", "Happy", "Workout", "Running", "TGIF", "K-Pop", "Anime")

    LazyVerticalGrid(
        GridCells.Fixed(2)
    ) {
        items(categories) { 
            BrowserItem(cat = it, drawable = R.drawable.music_browse)
        }
    }
}