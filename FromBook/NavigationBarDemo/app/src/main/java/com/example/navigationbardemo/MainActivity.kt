package com.example.navigationbardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationbardemo.screens.Contacts
import com.example.navigationbardemo.screens.Favorites
import com.example.navigationbardemo.screens.Home
import com.example.navigationbardemo.ui.theme.NavigationBarDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationBarDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Bottom Navigation Demo")})
        },
        content = { padding ->
            Column(Modifier.padding(padding)) {
                NavigationHost(navController)
            }
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    )
}

@Composable
fun NavigationHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.Home.route
    ) {
        composable(NavRoutes.Home.route) {
            Home()
        }
        composable(NavRoutes.Contacts.route) {
            Contacts()
        }
        composable(NavRoutes.Favorites.route) {
            Favorites()
        }

    }
}

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {
    NavigationBar {
        val backStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    navHostController.navigate(it.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(imageVector = it.image, contentDescription = null)
                },
                label = {
                    Text(it.title)
                }
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavigationBarDemoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        }
    }
}