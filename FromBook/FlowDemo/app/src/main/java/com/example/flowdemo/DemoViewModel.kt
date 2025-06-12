package com.example.flowdemo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

class DemoViewModel : ViewModel() {
    
    val myFlow: Flow<Int> = flow {
        //producer block
        for (i in 0..9) {
            emit(i)
            delay(2000)
        }
    }

    val newFlow = myFlow
        .map {
        "Current value = $it+2"
    }

    val newFlow2 = myFlow.transform { 
        emit("Value = $it")
        delay(1000)
        emit("Value doubled = ${it * 2}")
    }
    
    val myFLow2 = flowOf(2, 4, 5, 8)
    val myArrayFlow = arrayOf<String>("Red", "Green", "Blue").asFlow()
}