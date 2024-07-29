package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse
import com.example.rickandmortyapp.network.response.GetEpisodeByIdResponse
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
    fun getEpisodeById(
        @Path("episode-id") episodeId: Int,
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    fun getEpisodeRange(
        @Path("episode-range") episodeRange: String,
    ): Response<List<GetEpisodeByIdResponse>>
}