package com.example.rickandmortyapp.characters

import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse

class CharactersRepository {
    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPage(pageIndex)
        if (request.failed || !request.isSuccessful) {
            return null
        }
        return request.body
    }
}
