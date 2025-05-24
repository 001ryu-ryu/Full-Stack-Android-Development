package com.example.bookbuddy.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.bookbuddy.presentation.allbooksbycategory.navigation.Routes
import com.example.bookbuddy.presentation.effects.ImageAnimation

@Composable
fun Bookcart(
    imageUrl: String = "image",
    title: String = "title",
    author: String? = "author",
    des: String = "java ",
    navHostController: NavHostController,
    bookUrl: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navHostController.navigate(Routes.ShowPdfScreen(url = bookUrl))
            }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .height(150.dp)
                .padding(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    ImageAnimation()
                },
                error = {
                    Text(text = "Error while loading image")
                }
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                
            ) {
                Text(text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = des,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(4.dp))
                Text(text = author!!, fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 2, fontStyle = FontStyle.Italic)
            }
        }
    }
}





































