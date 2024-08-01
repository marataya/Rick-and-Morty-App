package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse

object CharacterMapper {
    fun buildFrom(
        response: GetCharacterByIdResponse,
        episodesList: List<GetEpisodeByIdResponse> = emptyList()
    ): CharacterModel {
        return CharacterModel(
            id = response.id,
            name = response.name,
            status = response.status,
            species = response.species,
            image = response.image,
            origin = CharacterModel.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            location = CharacterModel.Location(
                name = response.location.name,
                url = response.location.url
            ),
            gender = response.gender,
            episodesList = episodesList.map {
                EpisodeMapper.buildFrom(it)
            },
        )
    }
}