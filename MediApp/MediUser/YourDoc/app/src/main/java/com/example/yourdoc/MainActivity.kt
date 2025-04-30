package com.example.yourdoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yourdoc.ui.screens.SignUp
import com.example.yourdoc.ui.theme.YourDocTheme
import com.example.yourdoc.viewmodel.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModels<MyViewModel>()
            YourDocTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    
                    SignUp(viewModel.value)

                }
            }
        }
    }
}

