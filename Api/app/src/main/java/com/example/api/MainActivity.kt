package com.example.api

import android.os.Bundle
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
import com.example.api.myviewmodel.MyViewModel
import com.example.api.screens.HomeUi
import com.example.api.ui.theme.APITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = MyViewModel()
            APITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeUi(viewModel)
                }
            }
        }
    }
}
