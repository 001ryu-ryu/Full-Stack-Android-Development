package com.example.bookbuddy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.example.bookbuddy.data.model.BookCategory

@Composable
fun CategoriesTab(
    onClick: (String) -> Unit
) {
    val categoryList = listOf(
        BookCategory(
            categoryName = "Java",
            categoryImageUrl = "https://t3.ftcdn.net/jpg/04/51/12/88/360_F_451128839_vmKOyil368UoXcac46W7aaqelTtLuNFk.jpg"
        ),
        BookCategory(
            categoryName = "Android",
            categoryImageUrl = "https://i.pinimg.com/736x/15/13/0d/15130d602f33418c678cc32b017f5997.jpg"
        ),
        BookCategory(
            categoryName = "Quantum Physics",
            categoryImageUrl = "https://www.thebrighterside.news/uploads/2025/01/qua-1.webp?format=auto&optimize=high&width=1440"
        ),
        BookCategory(
            categoryName = "Artificial Intelligence",
            categoryImageUrl = "https://urbeuniversity.edu/post_assets/Le9zsr8bQmv7gmZW40UXiVaPsGcpVwaY65mw28tU.webp"
        ),



        )
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(categoryList) { category ->
            CategoriesItem(
                category = category.categoryName,
                categoryImage = category.categoryImageUrl,
                onClick = { onClick(category.categoryName) })
        }
    }
}

@Composable
private fun CategoriesItem(
    category: String,
    categoryImage: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                SubcomposeAsyncImage(
                    model = categoryImage,
                    contentDescription = null,
                    loading = { Loader() },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(text = category,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center)
        }
    }

}






















