package com.example.rickandmortyapp.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmortyapp.Constants

class LocationsViewModel : ViewModel() {
    private val repo = LocationRepo()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        LocationsPagingSource(repo)
    }.flow.cachedIn(viewModelScope)

}