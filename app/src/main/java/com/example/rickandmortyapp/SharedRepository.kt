package com.example.rickandmortyapp

import com.example.rickandmortyapp.domain.mappers.CharacterMapper
import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): CharacterModel? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)

        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes
        )
    }

    private suspend fun getEpisodesFromCharacterResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdResponse> {
        val episodeRange = characterResponse.episode?.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()
        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }
}
