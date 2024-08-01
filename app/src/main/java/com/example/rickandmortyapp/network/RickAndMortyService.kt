package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse
import com.example.rickandmortyapp.network.response.GetEpisodesPageResponse
import com.example.rickandmortyapp.network.response.GetLocationByIdResponse
import com.example.rickandmortyapp.network.response.GetLocationsPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Int,
    ): Response<GetCharacterByIdResponse>

    @GET("character/")
    suspend fun getCharactersPage(
        @Query("page") pageIndex: Int,
    ): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int,
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeRange: String,
    ): Response<List<GetEpisodeByIdResponse>>

    @GET("episode/")
    suspend fun getEpisodesPage(
        @Query("page") pageIndex: Int,
    ): Response<GetEpisodesPageResponse>


    @GET("character/{character-range}")
    suspend fun getCharacterRange(
        @Path("character-range") characterRange: String,
        ): Response<List<GetCharacterByIdResponse>>

    @GET("location/{location-id}")
    suspend fun getLocationById(
        @Path("location-id") locationId: Int
    ): Response<GetLocationByIdResponse>

    @GET("location/")
    suspend fun getLocationsPage(
        @Query("page") pageIndex: Int
    ): Response<GetLocationsPageResponse>
}