package com.example.bookbuddy.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookbuddy.R

@Composable
fun BooksList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            BookItem {  }
        }
    }
}

@Composable
fun BookItem(
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
            modifier = Modifier
                .size(70.dp)
                .padding(4.dp)
                .clip(CircleShape)) {
                //todo here will be async image
            Image(
                painter = painterResource(id = R.drawable.dhm3wjckp02d1),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
            Spacer(Modifier.width(5.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Book Name",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp))
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "by Book Author",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}




























