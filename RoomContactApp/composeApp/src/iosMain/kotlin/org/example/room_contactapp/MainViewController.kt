package org.example.room_contactapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.room_contactapp.data.database.DatabaseFactory
import org.example.room_contactapp.di.initKoin
import org.example.room_contactapp.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {

    App() }