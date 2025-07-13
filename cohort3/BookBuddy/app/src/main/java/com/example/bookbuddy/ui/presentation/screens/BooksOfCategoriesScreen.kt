package com.example.bookbuddy.ui.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.bookbuddy.components.NavBackIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksOfCategoriesScreen(
    booksOfCategory: String,
    navHostController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(booksOfCategory) },
                navigationIcon = { NavBackIcon() {navHostController.popBackStack()} }
            )
        },
    ) { innerPadding ->
        Text(
            text = "All books of $booksOfCategory will be here",
            modifier = Modifier.padding(innerPadding),
            textAlign = TextAlign.Center
        )
    }
}





























