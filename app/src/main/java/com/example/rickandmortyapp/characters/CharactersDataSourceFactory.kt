package com.example.rickandmortyapp.characters

import androidx.paging.DataSource
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope

class CharactersDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: CharactersRepo
) : DataSource.Factory<Int, GetCharacterByIdResponse>() {
    override fun create(): DataSource<Int, GetCharacterByIdResponse> {
        return CharactersDataSource(coroutineScope, repository)
    }
}