package com.example.simplerick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.simplerick.screens.CharacterDetailsScreen
import com.example.simplerick.ui.theme.SimpleRickTheme

class MainActivity : ComponentActivity() {
    private val ktorClient = KtorClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var character by remember { mutableStateOf<Character?>(null) }

            LaunchedEffect(Unit) {
               character = ktorClient.getCharacter(55)
            }
            SimpleRickTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CharacterDetailsScreen(
                        ktorClient = ktorClient,
                        characterId = 1,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
