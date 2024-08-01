package com.example.rickandmortyapp.characters

import com.example.rickandmortyapp.domain.mappers.CharacterMapper
import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.RickAndMortyCache
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse

class CharactersRepo {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }

    suspend fun getCharacterById(characterId: Int): CharacterModel? {
        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            return cachedCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }

        val episodesList = getEpisodesFromCharacterResponse(request.body)

        val character = CharacterMapper.buildFrom(
            response = request.body,
            episodesList = episodesList
        )

        RickAndMortyCache.characterMap[characterId] = character
        return character
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
