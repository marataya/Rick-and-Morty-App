package com.example.rickandmortyapp

class SharedRepository {

    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)


        if (request.failed || !request.isSuccessful) {
            return null
        }

        return request.body
    }
}
