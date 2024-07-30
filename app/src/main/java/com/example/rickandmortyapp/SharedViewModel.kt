package com.example.rickandmortyapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.network.RickAndMortyCache
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val sharedRepo = SharedRepository()

    private val _characterById = MutableLiveData<CharacterModel?>()
    val characterById: LiveData<CharacterModel?>
        get() = _characterById

    fun fetchCharacter(characterId: Int) {
        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        cachedCharacter?.let {
            _characterById.postValue(it)
            return
        }

        viewModelScope.launch {
            val response = sharedRepo.getCharacterById(characterId)
            _characterById.postValue(response)
            response?.let {
                RickAndMortyCache.characterMap[characterId] = response
            }
        }
    }
}