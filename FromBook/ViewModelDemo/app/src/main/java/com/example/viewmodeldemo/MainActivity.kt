package com.example.viewmodeldemo

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodeldemo.ui.theme.ViewModelDemoTheme
import com.example.viewmodeldemo.viewmodels.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    TopLevel(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TopLevel(modifier: Modifier = Modifier, model: MyViewModel = viewModel()) {

    val countState = model.count

    MainScreen(
        count = countState.value,
        onClick = {
            model.incCount()
        }
    )
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, count: Int, onClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Count is $count")

        Button(
            onClick = {
                onClick()
            }
        ) {
            Text("Increase")
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowPreview() {
    ViewModelDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            TopLevel(modifier = Modifier.padding(innerPadding))
        }
    }
}

















