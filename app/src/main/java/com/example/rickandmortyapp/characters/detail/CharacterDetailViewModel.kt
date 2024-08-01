package com.example.rickandmortyapp.characters.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.characters.CharactersRepo
import com.example.rickandmortyapp.domain.models.CharacterModel
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {

    private val repo = CharactersRepo()

    private val _characterById = MutableLiveData<CharacterModel?>()
    val characterById: LiveData<CharacterModel?>
        get() = _characterById

    fun fetchCharacter(characterId: Int) = viewModelScope.launch {
        val character = repo.getCharacterById(characterId)
        _characterById.postValue(character)
    }
}
