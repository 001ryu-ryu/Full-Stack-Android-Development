package com.example.musicplayerui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicplayerui.AccountDialog
import com.example.musicplayerui.MainViewModel
import com.example.musicplayerui.Screen
import com.example.musicplayerui.screensInBottom
import com.example.musicplayerui.screensInDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    
    // ScaffoldState is used to manage the state of the Scaffold, such as showing or hiding a Snackbar or handling drawer state.
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    // opening and closing the drawer is suspend fun so we will need a coroutine scope
    val scope: CoroutineScope = rememberCoroutineScope()
    val dialogOpen = remember {
        mutableStateOf(false)
    }

    val viewModel: MainViewModel = viewModel()
    val currentScreen = remember { 
        viewModel.currentScreen.value
    }

    //below 3 lines allow us to find out on which "View" we current are
    val controller: NavHostController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val title = remember {
        // change that to currentScreen.title
        mutableStateOf(currentScreen.title)
    }

    val bottomBar: @Composable () -> Unit = {
        if (currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home) {
            BottomNavigation(modifier = Modifier.wrapContentSize()) {

                screensInBottom.forEach { 
                    item ->
                    val isSelected = currentRoute == item.bRoute
                    val tint = if (isSelected) Color.White else Color.Black
                    BottomNavigationItem(
                        selected = currentRoute == item.bRoute, icon = {

                            Icon(tint = tint,painter = painterResource(id = item.icon), contentDescription = null)
                        },
                        onClick = {
                            
                            controller.navigate(item.bRoute)
                            title.value = item.bTitle
                        },
                        label = {
                            Text(text = item.bTitle, color = tint)
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }
    
    
    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                title = {Text(title.value)},
                navigationIcon = {
                    IconButton(onClick = {
                        // Open the drawer
                        scope.launch { 
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                    }
                }
            )
        },
        
        scaffoldState = scaffoldState,
        drawerContent = {
            LazyColumn(
                Modifier.padding(16.dp)
            ) {

                items(screensInDrawer) { 
                    item ->
                    DrawerItem(selected = currentRoute == item.dRoute, item = item) {

                        scope.launch { 
                            scaffoldState.drawerState.close() // It closes the drawer whether we tap on side or an item
                        }
                        if (item.dRoute == "add_account") {
                            // Open dialog
                            dialogOpen.value = true
                        } else {
                            controller.navigate(item.dRoute)
                            title.value = item.dTitle
                        }
                    }
                }
            }
        }
    ) {
        Navigation(navController = controller, viewModel = viewModel, pd = it)
        AccountDialog(dialogOpen)
    }
}

@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    val background = if (selected) Color.DarkGray else Color.White
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background)
            .clickable {
                onDrawerItemClicked()
            }
               
            
    ) {

        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )

        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel, pd: PaddingValues) {
    NavHost(navController = navController, startDestination = Screen.DrawerScreen.Account.route,
        modifier = Modifier.padding(pd)) { 
        
        composable(Screen.BottomScreen.Home.bRoute) { 
            //TODO add Home Screen
            HomeScreen()
        }

        composable(Screen.BottomScreen.Browse.bRoute) {
            //TODO add Home Screen
            Browse()
            
        }

        composable(Screen.BottomScreen.Library.bRoute) {
            //TODO add Home Screen
            Library()
        }
        
        composable(Screen.DrawerScreen.Account.route) { 
            AccountView()
        }
        composable(Screen.DrawerScreen.Subscription.route) { 
            Subscription()
        }
    }
}