package com.example.bookbuddy.presentation.allbooksbycategory.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.bookbuddy.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val urlHandler = LocalUriHandler.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    ModalNavigationDrawer(
        drawerState = drawerState, gesturesEnabled = true, drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(250.dp)
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text(text = "Home")
                    }, selected = true, icon = {
                        Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                    }, onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    })
                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text("Version 1.0")

                    }, selected = false, icon = {
                        Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                    }, onClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                        Toast.makeText(context, "Version 1.0", Toast.LENGTH_LONG).show()

                    })

                    HorizontalDivider()
                    NavigationDrawerItem(label = {
                        Text("Source Code")

                    }, selected = false, icon = {
                        Icon(imageVector = Icons.Filled.Code, contentDescription = null)
                    }, onClick = {
                        urlHandler.openUri("github.com")

                    })
                }
            }
        }) {
        Scaffold(
            modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Book Library", maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { 
                                    drawerState.open()
                                }
                            }
                        ) { 
                            Icon(painter = painterResource(id = R.drawable.books), contentDescription = null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) {
            innerPadding ->
            Column(
                Modifier.padding(innerPadding)
                    .fillMaxSize()
            ) {
                TabScreen(navHostController)

            }

        }
    }

}