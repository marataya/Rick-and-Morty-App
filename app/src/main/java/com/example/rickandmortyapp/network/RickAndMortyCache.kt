package com.example.rickandmortyapp.network

import com.example.rickandmortyapp.domain.models.CharacterModel

object RickAndMortyCache {
    val characterMap = mutableMapOf<Int, CharacterModel>()
}