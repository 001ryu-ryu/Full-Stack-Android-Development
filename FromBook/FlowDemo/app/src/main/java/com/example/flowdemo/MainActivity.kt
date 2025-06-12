package com.example.flowdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flowdemo.ui.theme.FlowDemoTheme
import com.example.flowdemo.StateFlowViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.reduce
import kotlin.system.measureTimeMillis

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Composable
fun ScreenSetup(modifier: Modifier = Modifier, viewModel: SharedFlowViewModel = viewModel()) {
//    MainScreen(modifier, viewModel.myFlow)
    
//    StateFlowMainScreen(modifier, viewModel)
    
    SharedFlowMainScreen(modifier, viewModel)
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, flow: Flow<Int>) {
//    val count by flow.collectAsState(initial = 0)

//    var count by remember { mutableStateOf<String>("Current value2 = ") }
    
    var count by remember { mutableStateOf<Int>(0) }

    LaunchedEffect(Unit) {
//        try {
//            flow.collect {
//                count2 = it
//            }
//        } finally {
//            count2 = "Flow stream ended"
//        }
        
//        val elapsedTime = measureTimeMillis {
//            flow
//                .buffer()
//                .collect {
//                count2 = it
//                delay(1000)
//            }
//        }
//        count2 = "Duration = $elapsedTime"
        
        flow.reduce { accumulator, value ->
            count = accumulator
            accumulator + value
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))

    }
}

@Composable
fun StateFlowMainScreen(modifier: Modifier = Modifier, viewModel: StateFlowViewModel) {
    val count by viewModel.stateFlow.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
        Button(
            onClick = {
                viewModel.increaseValue()
            }
        ) {
            Text("Click Me")
        }

    }
}

@Composable
fun SharedFlowMainScreen(modifier: Modifier = Modifier, viewModel: SharedFlowViewModel) {
    val count by viewModel.sharedFlow.collectAsState(initial = 0)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
        Button(
            onClick = {
                viewModel.startSharedFlow()
            }
        ) {
            Text("Click Me")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FlowDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ScreenSetup(Modifier.padding(innerPadding))

        }
    }
}






























