package com.example.rickandmortyapp.episodes

import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetEpisodesPageResponse

class EpisodeRepo {
    suspend fun fetchEpisodePage(pageIndex: Int): GetEpisodesPageResponse? {
        val pageRequest = NetworkLayer.apiClient.getEpisodesPage(pageIndex)
        if (!pageRequest.isSuccessful) {
            return null
        }

        return pageRequest.body
    }

}