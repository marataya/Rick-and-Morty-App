package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodesPageResponse
import com.example.rickandmortyapp.network.response.GetLocationByIdResponse
import com.example.rickandmortyapp.network.response.GetLocationsPageResponse
import retrofit2.Response

class ApiClient(private val service: RickAndMortyService) {
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { service.getCharacterById(characterId) }
    }

    suspend fun getCharacterRange(characterRange: String): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { service.getCharacterRange(characterRange) }
    }

    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { service.getCharactersPage(pageIndex) }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { service.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { service.getEpisodeRange(episodeRange) }
    }

    suspend fun getEpisodesPage(pageIndex: Int): SimpleResponse<GetEpisodesPageResponse> {
        return safeApiCall { service.getEpisodesPage(pageIndex) }
    }

    suspend fun getLocationById(locationId: Int): SimpleResponse<GetLocationByIdResponse> {
        return safeApiCall { service.getLocationById(locationId) }
    }

    suspend fun getLocationsPage(pageIndex: Int): SimpleResponse<GetLocationsPageResponse> {
        return safeApiCall { service.getLocationsPage(pageIndex) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}