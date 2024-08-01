package com.example.rickandmortyapp.episodes

import com.example.rickandmortyapp.domain.models.EpisodeModel

sealed class EpisodeUiModel {
    class Item(val episode: EpisodeModel) : EpisodeUiModel()
    class Header(val text: String) : EpisodeUiModel()
}