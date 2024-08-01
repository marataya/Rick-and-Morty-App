package com.example.rickandmortyapp.locations

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapp.domain.mappers.LocationMapper
import com.example.rickandmortyapp.domain.models.LocationModel
import com.example.rickandmortyapp.network.NetworkLayer

class LocationsPagingSource(private val repo: LocationRepo) : PagingSource<Int, LocationModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationModel> {
        val pageNumber = params.key ?: 1
        val prevKey = if (pageNumber == 1) null else pageNumber - 1

        val pageRequest = NetworkLayer.apiClient.getLocationsPage(pageNumber)

        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }
        //network call
        return LoadResult.Page(
            data = pageRequest.body.results.map { response ->
                LocationMapper.buildFrom(response)
            },
            prevKey = prevKey,
            nextKey = pageRequest.body.info.next?.split("?page=")?.get(1)?.toInt()
        )
    }

    override fun getRefreshKey(state: PagingState<Int, LocationModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}