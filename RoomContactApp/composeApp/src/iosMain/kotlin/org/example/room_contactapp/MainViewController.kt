package org.example.room_contactapp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.room_contactapp.data.database.DatabaseFactory
import org.example.room_contactapp.presentation.App

fun MainViewController() = ComposeUIViewController {
    val dbBuilder = remember { mutableStateOf(DatabaseFactory().create()) }
    App(dbBuilder.value) }