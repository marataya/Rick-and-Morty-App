package com.example.rickandmortyapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    private val sharedRepo = SharedRepository()

    private val _characterById = MutableLiveData<GetCharacterByIdResponse?>()
    val characterById: MutableLiveData<GetCharacterByIdResponse?>
        get() = _characterById

    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            val response = sharedRepo.getCharacterById(characterId)
            _characterById.postValue(response)
        }
    }
}