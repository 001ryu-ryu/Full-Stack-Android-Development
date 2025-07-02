package com.example.simplerick

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.network.KtorClient
import com.example.simplerick.screens.CharacterDetailsScreen
import com.example.simplerick.ui.theme.SimpleRickTheme

class MainActivity : ComponentActivity() {
    private val ktorClient = KtorClient()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleRickTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "character_details") {
                        composable("character_details") {
                            CharacterDetailsScreen(
                                ktorClient = ktorClient,
                                characterId = 1,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                navController.navigate("character_episodes/$it")
                            }
                        }

                        composable("character_episodes/{characterId}") {backStackEntry ->
                            val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: -1
                            CharacterEpisodeScreen(characterId)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun CharacterEpisodeScreen(characterId: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Character Episode Screen: $characterId", fontSize = 28.sp)
    }
}
