package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildFrom(networkEpisode: GetEpisodeByIdResponse): EpisodeModel {
        return EpisodeModel(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            seasonNumber = getSeasonNumberFromEpisodeString(networkEpisode.episode),
            episodeNumber = getEpisodeNumberFromEpisodeString(networkEpisode.episode)
        )
    }

    private fun getSeasonNumberFromEpisodeString(episode: String): Int {
        val endIndex = episode.indexOfFirst { it.equals('e', true) }
        if (endIndex == -1) {
            return 0
        }
        return episode.substring(1, endIndex).toIntOrNull() ?: 0
    }

    private fun getEpisodeNumberFromEpisodeString(episode: String): Int {
        val startIndex = episode.indexOfFirst { it.equals('e', true) }
        if (startIndex == -1) {
            return 0
        }
        return episode.substring(startIndex + 1).toIntOrNull() ?: 0
    }
}