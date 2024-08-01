package com.example.rickandmortyapp.domain.models

data class LocationModel(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val created: String = "",
    val residentsList: List<CharacterModel> = emptyList()
)