package com.example.bookbuddy.presentation.effects

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AnimatedShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnimation.value, translateAnimation.value)
    )

    ShimmerGridItem(brush)

}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(
                Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )

            Spacer(Modifier.padding(10.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(Modifier.padding(5.dp))

                Spacer(
                    Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(0.7f)
                        .background(brush)
                )

                Spacer(Modifier.padding(3.dp))

                Spacer(
                    Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(0.9f)
                        .background(brush)
                )

                Spacer(Modifier.padding(3.dp))

                Spacer(
                    Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(0.9f)
                        .background(brush)
                )

                Spacer(Modifier.padding(3.dp))

                Spacer(
                    Modifier
                        .size(16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(0.3f)
                        .background(brush)
                )
            }
        }
    }
}

@Composable
fun CategoryShimmer() {
    val shimmerColors = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnimation.value, translateAnimation.value)
    )

    Card(
        modifier = Modifier
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(150.dp)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Spacer(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.padding(8.dp))

            Spacer(
                Modifier
                    .height(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
                    .fillMaxWidth(0.5f)
            )
        }
    }
}

@Composable
fun ImageAnimation() {
    val shimmerColors = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(10f, 10f),
        end = Offset(translateAnimation.value, translateAnimation.value)
    )

    Spacer(Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)).background(brush))
}

















