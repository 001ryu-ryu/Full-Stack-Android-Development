package com.example.musicplayerui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.AddAccount)
    val currentScreen: State<Screen>
        get() = _currentScreen
    fun setCurrentScreen(screen: Screen) {
        _currentScreen.value = screen
    }
}