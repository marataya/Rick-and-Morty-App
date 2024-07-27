package com.example.rickandmortyapp.characters

import androidx.paging.PageKeyedDataSource
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.example.rickandmortyapp.network.response.GetCharactersPageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepository,
) : PageKeyedDataSource<Int, GetCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>,
    ) {
        coroutineScope.launch {
            val page = repository.getCharactersPage(1)
            if (page == null) {
                callback.onResult(emptyList(), null, null)
                return@launch
            }

            callback.onResult(page.results, null, getIndexOfPage(page))
        }
    }

    private fun getIndexOfPage(page: GetCharactersPageResponse) = page.info.next?.split("?page=")?.get(1)?.toInt()

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>,
    ) {
        coroutineScope.launch {
            val page = repository.getCharactersPage(params.key)
            if (page == null) {
                callback.onResult(emptyList(), null)
                return@launch
            }

            callback.onResult(page.results, getIndexOfPage(page))
        }

    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>,
    ) {
        //adsf
    }

}