package com.example.wishlistapp

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    Scaffold(
            topBar = { AppBar(title = "Wish Here") {
                
            }
            },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(20.dp),
                contentColor = Color.White,
                containerColor = Color.Black,
                elevation = FloatingActionButtonDefaults.elevation(),
                onClick = {
                    //Navigation to add screen here
                    navController.navigate(Screen.AddScreen.route +"/0L")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
            ) {
        innerPadding ->
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf())
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            
            items(wishlist.value, key = {wish -> wish.id// this key fixed the swiping error where it also deleted other cards too
            }) {
                wish -> 
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                            viewModel.deleteWish(
                                wish
                            )
                        }
                        true
                    }
                )
                SwipeToDismiss(state = dismissState, 
                    background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == DismissDirection.EndToStart)
                            Color.Red else Color.Green,
                            label = ""
                        )
                        var alignment = Alignment.CenterEnd
                        Box(
                            modifier = Modifier.fillMaxSize().background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                        }
                    },
                    directions = setOf(
                        DismissDirection.EndToStart),
                    dismissThresholds = {
                        FractionalThreshold(0.25f)
                    },
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id") // passing the id to the add screen
                        }
                    })
            }

        }

    }
    


}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    
    Card(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .clickable {
            onClick()
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, draggedElevation = 15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black)
        
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)

        }
    }
}