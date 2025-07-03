package com.example.contact

import android.annotation.SuppressLint
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
import com.example.contact.ui.navigation.NavApp
import com.example.contact.ui.screen.AddContactScreen
import com.example.contact.ui.screen.ContactDetailsScreen
import com.example.contact.ui.theme.ContactTheme
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    NavApp()
//                    ContactDetailsScreen(contactId = 2)
                }
            }
        }
    }
}


