package com.example.rickandmortyapp.locations

import com.example.rickandmortyapp.domain.mappers.EpisodeMapper
import com.example.rickandmortyapp.domain.mappers.LocationMapper
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.domain.models.LocationModel
import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodesPageResponse
import com.example.rickandmortyapp.network.response.GetLocationByIdResponse

class LocationRepo {
//    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
//        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)
//        if (!pageRequest.isSuccessful) {
//            return null
//        }
//
//        return pageRequest.body
//    }

    suspend fun getLocationById(locationId: Int): LocationModel? {
        val request = NetworkLayer.apiClient.getLocationById(locationId = locationId)

        if (!request.isSuccessful) {
            return null
        }

        val residentsList = getCharactersFromLocationResponse(request.body)

        return LocationMapper.buildFrom(
            location = request.body,
            residentsList = residentsList
        )
    }

    private suspend fun getCharactersFromLocationResponse(locationResponse: GetLocationByIdResponse): List<GetCharacterByIdResponse> {
        val characterRange = locationResponse.residents?.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()
        val request = NetworkLayer.apiClient.getCharacterRange(characterRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }

        return request.body
    }

}