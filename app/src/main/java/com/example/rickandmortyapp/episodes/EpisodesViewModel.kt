package com.example.rickandmortyapp.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmortyapp.Constants

class EpisodesViewModel : ViewModel() {
    private val repo = EpisodeRepo()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        EpisodePagingSource(repo)
    }.flow.cachedIn(viewModelScope)
}