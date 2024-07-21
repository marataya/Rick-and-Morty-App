package com.example.rickandmortyapp

import com.example.rickandmortyapp.network.NetworkLayer
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)


        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}
