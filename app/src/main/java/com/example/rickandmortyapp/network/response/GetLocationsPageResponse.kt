package com.example.rickandmortyapp.network.response

data class GetLocationsPageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetLocationByIdResponse> = emptyList()
)