package com.example.rickandmortyapp.episodes

import com.example.rickandmortyapp.domain.mappers.EpisodeMapper
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodesPageResponse

class EpisodeRepo {
    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)
        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }

    suspend fun getEpisodeById(episodeId: Int): EpisodeModel? {
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)

        if (!request.isSuccessful) {
            return null
        }

        val characterList = getCharactersFromEpisodeResponse(request.body)
        return EpisodeMapper.buildFrom(
            episode = request.body,
            charactersList = characterList
        )
    }

    private suspend fun getCharactersFromEpisodeResponse(episodeResponse: GetEpisodeByIdResponse): List<GetCharacterByIdResponse> {
        val characterRange = episodeResponse.characters?.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()
        val request = NetworkLayer.apiClient.getCharacterRange(characterRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

}