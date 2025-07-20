package com.example.bookbuddy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.bookbuddy.ui.navigation.Routes
import com.example.bookbuddy.ui.viewmodel.BookViewModel

@Composable
fun BooksList(
    bookViewModel: BookViewModel = hiltViewModel(),
    onClick: (String) -> Unit
) {
    val booksState = bookViewModel.booksState.collectAsState()

    booksState.value?.let { bookResultState ->
        when {
            bookResultState.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Loader()
                }
            }

            bookResultState.success.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(bookResultState.success) { book ->
                        BookItem(
                            bookName = book.bookName,
                            bookImage = book.bookImage,
                            bookAuthor = book.bookAuthor,
                            onClick = { onClick(book.bookUrl) }
                        )
                    }
                }
            }

            bookResultState.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Error: ${bookResultState.error.message}")
                }
            }
        }
    }
}





























