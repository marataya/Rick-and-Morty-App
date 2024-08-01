package com.example.rickandmortyapp.network.response

data class GetLocationByIdResponse(
    val id: Int = 0,
    val name: String = "",
    val type: String = "",
    val dimension: String = "",
    val residents: List<String> = listOf(),
    val created: String = ""
)