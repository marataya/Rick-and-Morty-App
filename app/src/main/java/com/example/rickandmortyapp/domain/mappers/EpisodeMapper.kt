package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun buildFrom(networkEpisode: GetEpisodeByIdResponse): EpisodeModel {
        return EpisodeModel(
            id = networkEpisode.id,
            name = networkEpisode.name,
            airDate = networkEpisode.air_date,
            episode = networkEpisode.episode,
        )
    }
}