package com.example.floweg

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.floweg.ui.theme.FlowegTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        GlobalScope.launch(Dispatchers.Main) {
//            //getting notes
//            getNotes()
//                .map {
//                    FormatedNote(it.isActive, it.title.uppercase(), it.description)
//            }
//                .filter {
//                    it.isActive
//                }
//                .collect {
//                    Log.d("TAG", it.toString())
//                }
//        }

//        GlobalScope.launch(Dispatchers.Main) {
////            val time = measureTimeMillis {
////
////            }
//            producer()
////                    .buffer(3)
//                .map {
//                    delay(1000)
//                    it * 2
//                    Log.d("TAG", "Map Thread - ${Thread.currentThread().name}")
//                }
//                .filter {
//                    delay(500)
//                    Log.d("TAG", "Filter Thread - ${Thread.currentThread().name}")
//                    it < 8
//                }
//                .flowOn(Dispatchers.IO)
//                .collect {
////                    delay(1500)
//                    Log.d("TAG", "Collector Thread - ${Thread.currentThread().name}")
//                }
//
////            Log.d("TAG", time.toString())
//
//        }
//        GlobalScope.launch(Dispatchers.Main) {
//
//            try {
//                producer()
//                    .collect {
//                        Log.d("TAG", "Collector Thread - ${Thread.currentThread().name}")
//                    }
//
//            } catch (e: Exception) {
//                Log.d("TAG", e.message.toString())
//            }
//
//
//
//
//        }

//
        // Shared Flow
//        val result = sharedProducer()
//
//        GlobalScope.launch {
//
//            result.collect {
//                Log.d("TAG1", "Item - $it")
//            }
//        }
//
//        GlobalScope.launch {
//
//            delay(2500)
//            result.collect {
//                Log.d("TAG2", "Item - $it")
//            }
//        }

        // State Flow
        GlobalScope.launch {
            val result = stateProducer()
            Log.d("TAG", result.value.toString())
        }

        setContent {
            FlowegTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }

            }
        }




    }
    fun producer() = flow<Int> {
        val list = listOf<Int>(1, 2, 3, 4, 5)
        list.forEach {
            delay(1000)
            Log.d("TAG", "Emitter Thread - ${Thread.currentThread().name}")
            emit(it)
            throw Exception("Error in Emitting")
        }
    }.catch {
        Log.d("TAG", "Emitter Catch - ${it.message}")
        emit(-1)
    }

    //Shared Flow
    fun sharedProducer(): Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 2)
        GlobalScope.launch {
            val list = listOf(
                1, 2, 3, 4, 5
            )
            list.forEach {
                mutableSharedFlow.emit(it)
                delay(1000)
            }
        }
        return mutableSharedFlow
    }

    // State Flow
    fun stateProducer(): StateFlow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        GlobalScope.launch {
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }
}