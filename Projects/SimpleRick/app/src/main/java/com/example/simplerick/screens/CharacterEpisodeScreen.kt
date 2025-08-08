package com.example.simplerick.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network.KtorClient
import com.example.network.models.domain.Character
import com.example.network.models.domain.Episode
import com.example.simplerick.components.character.CharacterDetailsNamePlateComponent
import com.example.simplerick.components.character.DataPoint
import com.example.simplerick.components.character.DataPointComponent
import com.example.simplerick.components.common.CharacterImage
import com.example.simplerick.components.common.CharacterNameComponent
import com.example.simplerick.components.episode.EpisodeRowComponent
import kotlinx.coroutines.launch

@Composable
fun CharacterEpisodeScreen(characterId: Int, ktorClient: KtorClient) {
    var characterState by remember { mutableStateOf<Character?>(null) }
    var episodeState by remember { mutableStateOf<List<Episode>>(emptyList()) }

    LaunchedEffect(key1 = Unit, block = {
        ktorClient.getCharacter(id = characterId).onSuccess { character ->
            characterState = character
            launch {
                ktorClient.getEpisode(character.episodeIds).onSuccess { episodes ->
                    episodeState = episodes
                }.onFailure {

                }
            }
        }
            .onFailure {
            }
    })

    characterState?.let { character ->
        MainScreen(character = character, episodes = episodeState)
    } ?: LoadingState()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }
    LazyColumn(
        modifier = Modifier.systemBarsPadding(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item { CharacterNameComponent(character.name) }
        item { Spacer(Modifier.height(16.dp)) }
        item {
            LazyRow {
                episodeBySeasonMap.forEach { entry ->
                    val title = "Season ${entry.key}"
                    val description = "${entry.value.size} ep"
                    item {
                        DataPointComponent(dataPoint = DataPoint(title, description))
                        Spacer(Modifier.width(16.dp))
                    }

                }
            }
        }
        item { CharacterImage(imageUrl = character.imageUrl) }

        episodeBySeasonMap.forEach { mapEntry ->
            stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
            items(mapEntry.value) { episode ->
                EpisodeRowComponent(episode = episode)
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Text(
        text = "Season $seasonNumber",
        color = Color.Cyan.copy(alpha = 0.6f),
        fontSize = 32.sp,
        lineHeight = 32.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .border(
                width = 1.dp,
                color = Color.Cyan.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp)
    )
}





































