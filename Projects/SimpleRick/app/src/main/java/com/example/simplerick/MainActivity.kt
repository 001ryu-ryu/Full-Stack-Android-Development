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
import androidx.navigation.toRoute
import com.example.network.KtorClient
import com.example.simplerick.navigation.Routes
import com.example.simplerick.screens.CharacterDetailsScreen
import com.example.simplerick.screens.CharacterEpisodeScreen
import com.example.simplerick.ui.theme.SimpleRickTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var ktorClient: KtorClient
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimpleRickTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = Routes.CharacterDetailsScreen) {
                        composable<Routes.CharacterDetailsScreen> {
                            CharacterDetailsScreen(
                                characterId = 4,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                navController.navigate(Routes.CharacterEpisodeScreen(it))
                            }
                        }

                        composable<Routes.CharacterEpisodeScreen> { backStackEntry ->
                            val characterId = backStackEntry.toRoute<Routes.CharacterEpisodeScreen>().characterId
                            CharacterEpisodeScreen(characterId, ktorClient)
                        }
                    }

                }
            }
        }
    }
}

