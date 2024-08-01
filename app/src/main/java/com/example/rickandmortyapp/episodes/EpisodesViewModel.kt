package com.example.rickandmortyapp.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import com.example.rickandmortyapp.Constants
import kotlinx.coroutines.flow.map

class EpisodesViewModel : ViewModel() {
    private val repo = EpisodeRepo()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodesPagingSource(repo)
    }.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { episodeUiModel1: EpisodeUiModel?, episodeUiModel2: EpisodeUiModel? ->
            // Initial separator for the first season header (before the whole list)
            if (episodeUiModel1 == null) {
                return@insertSeparators EpisodeUiModel.Header("Season 1")
            }

            // No footer
            if (episodeUiModel2 == null) {
                return@insertSeparators null
            }

            // Make sure we only care about the items (episodes)
            if (episodeUiModel1 is EpisodeUiModel.Header || episodeUiModel2 is EpisodeUiModel.Header) {
                return@insertSeparators null
            }

            // Little logic to determine if a separator is necessary
            val episode1 = (episodeUiModel1 as EpisodeUiModel.Item).episode
            val episode2 = (episodeUiModel2 as EpisodeUiModel.Item).episode
            return@insertSeparators if (episode2.seasonNumber != episode1.seasonNumber) {
                EpisodeUiModel.Header("Season ${episode2.seasonNumber}")
            } else {
                null
            }
        }
    }
}