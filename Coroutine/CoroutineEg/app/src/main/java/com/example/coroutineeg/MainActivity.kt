package com.example.coroutineeg

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
import com.example.coroutineeg.ui.theme.CoroutineEgTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        CoroutineScope(Dispatchers.Main).launch {
//            task1()
//        }
//
//        CoroutineScope(Dispatchers.Main).launch {
//            task2()
//        }

        CoroutineScope(Dispatchers.IO).launch {
            printFollowers()
            withAsync()
        }
        setContent {
            CoroutineEgTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }

    }


//    suspend fun task1() {
//        Log.d("TAG", "Starting task 1")
//        yield() // we can use delay also
//        Log.d("TAG", "Ending task 1")
//    }
//
//    suspend fun task2() {
//        Log.d("TAG", "Starting task 2")
//        yield()
//        Log.d("TAG", "Ending task 2")
//    }



    // We are seeing jobs here
    private suspend fun printFollowers() {
        var instaFollowers = 0
        var ytSubs = 0
        val job1 = CoroutineScope(Dispatchers.IO).launch {//we can use async inside launch to reduce time
            instaFollowers = getInstaFollowers()
        }

        val job2 = CoroutineScope(Dispatchers.IO).launch {
            ytSubs = getYtSubs()
        }
        job1.join() // jab tak yeh coroutine complete nehi ho jata tab tak age wali line execute nehi hogi
        job2.join()

        Log.d("TAG-job", "Insta- $instaFollowers, Yt- $ytSubs")
    }

    // We are seeing async and await here
    private suspend fun withAsync() {
        val insta = CoroutineScope(Dispatchers.IO).async {
            getInstaFollowers()
        }.await()

        val yt = CoroutineScope(Dispatchers.IO).async {
            getYtSubs()
        }

        Log.d("TAG-async", "Insta - $insta  Yt - ${yt.await()}")
    }


    private suspend fun getInstaFollowers(): Int {
        delay(1000)
        return 17
    }


    private suspend fun getYtSubs(): Int {
        delay(2000)
        return 3400
    }
}
