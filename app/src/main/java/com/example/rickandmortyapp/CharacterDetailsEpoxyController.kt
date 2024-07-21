package com.example.rickandmortyapp

import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsDatapointBinding
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmortyapp.epoxy.LoadingEpoxyModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.example.rickandmortyapp.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController : EpoxyController() {
    var isLoading:Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var characterResponse: GetCharacterByIdResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            LoadingEpoxyModel().id("loading").addTo(this)
        }

        if (characterResponse == null) {
            return
        }

        HeaderEpoxyModel(
            name = characterResponse!!.name,
            gender = characterResponse!!.gender
        ).id("header").addTo(this)

        ImageEpoxyModel(
            imageUrl = characterResponse!!.image
        ).id("image").addTo(this)

        DataPointEpoxyModel(
            title = "Origin",
            description = characterResponse!!.origin!!.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = characterResponse!!.species
        ).id("data_point_2").addTo(this)
    }

    data class HeaderEpoxyModel (
        val name: String,
        val gender: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            fullNameView.text = name
            if (gender.equals("male", true)) {
                genderView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderView.setImageResource(R.drawable.ic_female_24)
            }
        }
    }


    data class ImageEpoxyModel(
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {

        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imageUrl).into(headerImage)
        }
    }

    data class DataPointEpoxyModel(
        val title: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDatapointBinding>(R.layout.model_character_details_datapoint) {

        override fun ModelCharacterDetailsDatapointBinding.bind() {
            labelTextView.text = title
            textView.text = description
        }
    }
}