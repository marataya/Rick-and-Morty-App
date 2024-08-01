package com.example.rickandmortyapp.domain.models

data class CharacterModel(
    val id: Int = 0,
    val image: String = "",
    val gender: String = "",
    val status: String = "",
    val species: String = "",
    val origin: Origin,
    val name: String = "",
    val location: Location,
    val episodesList: List<EpisodeModel> = listOf(),
) {
    data class Origin(
        val name: String = "",
        val url: String = "",
    )

    data class Location(
        val name: String = "",
        val url: String = "",
    )

    data class Episode(
        val id: Int = 0,
        val name: String = "",
        val air_date: String = "",
        val episode: String = "",
    )
}

