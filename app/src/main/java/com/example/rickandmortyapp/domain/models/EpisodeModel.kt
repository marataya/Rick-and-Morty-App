package com.example.rickandmortyapp.domain.models

data class EpisodeModel(
    val id: Int = 0,
    val name: String = "",
    val airDate: String = "",
    val seasonNumber: Int = 0,
    val episodeNumber: Int = 0,
    val charactersList: List<CharacterModel> = emptyList()
) {
    fun getFormattedSeason(): String {
        return "Season $seasonNumber Episode $episodeNumber"
    }

    fun getFormattedSeasonTruncated(): String {
        return "S.$seasonNumber E.$episodeNumber"
    }
}