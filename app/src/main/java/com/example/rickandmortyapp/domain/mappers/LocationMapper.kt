package com.example.rickandmortyapp.domain.mappers

import com.example.rickandmortyapp.domain.models.LocationModel
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetLocationByIdResponse

object LocationMapper {
    fun buildFrom(
        location: GetLocationByIdResponse,
        residentsList: List<GetCharacterByIdResponse> = emptyList()
    ): LocationModel {
        return LocationModel(
            id = location.id,
            name = location.name,
            type = location.type,
            dimension = location.dimension,
            created = location.created,
            residentsList = residentsList.map {
                CharacterMapper.buildFrom(it)
            }
        )
    }
}