package com.example.simplerick.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.simplerick.components.character.CharacterDetailsNamePlateComponent
import com.example.simplerick.components.character.DataPoint
import com.example.simplerick.components.character.DataPointComponent
import kotlinx.coroutines.delay

@Composable
fun CharacterDetailsScreen(
    ktorClient: KtorClient,
    characterId: Int,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    var character by remember { mutableStateOf<Character?>(null) }

    val characterDataPoints: List<DataPoint> by remember {
        derivedStateOf {
            buildList {
                character?.let {character ->
                    add(DataPoint(title = "Last known location", description = character.location.name))
                    add(DataPoint(title = "Species", description = character.species))
                    add(DataPoint(title = "Gender", description = character.gender.displayName))
                    character.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataPoint("Type", type))
                    }
                    add(DataPoint(title = "Origin", description = character.origin.name))
                    add(DataPoint(title = "Episode count", description = character.episodeUrls.size.toString()))

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        ktorClient
            .getCharacter(characterId)
            .onSuccess {
                character = it
            }.onFailure {
                // todo handle exception
            }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        if(character == null) {
           item { LoadingState() }
            return@LazyColumn

        }

        //name plate
        item {
            CharacterDetailsNamePlateComponent(
                name = character!!.name,
                status = character!!.status
            )
        }

        item { Spacer(Modifier.height(8.dp)) }

        //Image
        item {
            SubcomposeAsyncImage(
                model = character!!.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                loading = {LoadingState()}
            )
        }

        // data points
        items(characterDataPoints) {
            Spacer(Modifier.height(32.dp))
            DataPointComponent(
                dataPoint = it
            )
        }

        item { Spacer(Modifier.height(32.dp)) }

        // Button

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "View all episodes",
                    color = Color.Cyan,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Cyan,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = {onClick(characterId)})
                        .padding(vertical = 8.dp, horizontal = 32.dp)
                )
            }

        }

        item { Spacer(Modifier.height(64.dp)) }
    }
}

@Composable
fun LoadingState() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(128.dp)
    )
}

















