package com.example.listdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listdemo.ui.theme.ListDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    ColumnList(modifier)
}

@Composable
fun ColumnList(modifier: Modifier = Modifier) {
    val scrollStata = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier) {
        Row {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollStata.animateScrollTo(0)
                    }

                },
                modifier = Modifier.weight(0.5f)
                    .padding(2.dp)
            ) {
                Text("Top")
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollStata.scrollTo(scrollStata.maxValue)
                    }

                },
                modifier = Modifier.weight(0.5f)
                    .padding(2.dp)
            ) {
                Text("End")
            }
        }

        Column(modifier
            .fillMaxWidth()
            .verticalScroll(scrollStata)) {
            repeat(500) {
                Text("List Item $it",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(50.dp))
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListPreview() {
    ListDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MainScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}