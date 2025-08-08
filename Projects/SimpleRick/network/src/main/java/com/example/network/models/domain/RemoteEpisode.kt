package com.example.network.models.domain

import kotlinx.serialization.Serializable

@Serializable
data class RemoteEpisode(
    val id: Int,
    val name: String,
    val episode: String,
    val air_date: String,
    val characters: List<String>
)

fun RemoteEpisode.toDomainEpisode(): Episode {
    return Episode(
        id = id,
        name = name,

        //S03E07, filter out the digits, take 2, for episode filter out the digits take the last 2
        seasonNumber = episode.filter { it.isDigit() }.take(2).toInt(),
        episodeNumber = episode.filter { it.isDigit() }.takeLast(2).toInt(),
        airDate = air_date,
        characterIdsInEpisode = characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1).toInt()
        }
    )
}
