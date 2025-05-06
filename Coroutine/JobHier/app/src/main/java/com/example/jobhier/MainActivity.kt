package com.example.jobhier

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
import com.example.jobhier.ui.theme.JobHierTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        GlobalScope.launch {
            //execute()
            executeCancellation()
        }
        setContent {
            JobHierTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }

    private suspend fun execute() {
        var parentJob = GlobalScope.launch(Dispatchers.Main) {
            Log.d("TAG - Parent", "Parent Started")

            var childJob = launch(Dispatchers.IO) {
                Log.d("TAG - Child", "Child Started")
                delay(5000)
                Log.d("TAG - child", "Child Killed")
            }

            delay(3000)
            Log.d("TAG - Parent", "Parent Killed")

        }
        delay(1000)
        parentJob.cancel()
        parentJob.join()
        Log.d("TAG - Parent", "Parent Completed")
    }

    private suspend fun executeCancellation() {
        val parentJob = CoroutineScope(Dispatchers.IO).launch {
            for (i in 1..1000) {
                if (isActive) {
                    executeLong()
                    Log.d("TAG - long", i.toString())
                }
            }
        }

        delay(100)
        Log.d("TAG", "Cancelling Job")
        parentJob.cancel()
        Log.d("TAG", "Cancelled Job")
        parentJob.join()
        Log.d("TAG", "Parent Completed")
    }


    private fun executeLong() {
        for (i in 1..1000000) {

        }
    }
}
