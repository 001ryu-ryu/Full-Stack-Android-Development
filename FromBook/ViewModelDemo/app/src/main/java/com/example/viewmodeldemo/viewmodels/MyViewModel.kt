package com.example.viewmodeldemo.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var count = mutableIntStateOf(0)

    fun incCount() {
        count.intValue++
    }
}