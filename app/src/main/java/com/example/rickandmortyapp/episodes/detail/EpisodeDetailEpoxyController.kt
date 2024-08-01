package com.example.rickandmortyapp.episodes.detail

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ModelCharacterListItemBinding
import com.example.rickandmortyapp.databinding.ModelCharacterListItemSquareBinding
import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class EpisodeDetailEpoxyController(
    val charactersList: List<CharacterModel>
) : EpoxyController() {
    override fun buildModels() {
        charactersList.forEach {
            CharacterEpoxyModel(
                imageUrl = it.image,
                name = it.name
            ).id("episode_${it.id}").addTo(this)
        }
    }

    data class CharacterEpoxyModel(
        val imageUrl: String,
        val name: String
    ) : ViewBindingKotlinModel<ModelCharacterListItemSquareBinding>(R.layout.model_character_list_item_square) {
        override fun ModelCharacterListItemSquareBinding.bind() {
            Picasso.get().load(imageUrl).into(characterImageView)
            characterNameTextView.text = name
        }

    }

}
