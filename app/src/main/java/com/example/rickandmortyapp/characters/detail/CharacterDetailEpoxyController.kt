package com.example.rickandmortyapp.characters.detail

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsDatapointBinding
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsHeaderBinding
import com.example.rickandmortyapp.databinding.ModelCharacterDetailsImageBinding
import com.example.rickandmortyapp.databinding.ModelEpisodeCarouselItemBinding
import com.example.rickandmortyapp.databinding.ModelTitleBinding
import com.example.rickandmortyapp.domain.models.CharacterModel
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.epoxy.LoadingEpoxyModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel
import com.squareup.picasso.Picasso

class CharacterDetailEpoxyController(
    private val onEpisodeClicked: (Int) -> Unit
) : EpoxyController() {
    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var character: CharacterModel? = null
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

        if (character == null) {
            return
        }

        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)

        ImageEpoxyModel(
            imageUrl = character!!.image
        ).id("image").addTo(this)

        //Episode Carousel

        val items = character!!.episodesList.map {
            EpisodeCarouselItem(
                it,
                onEpisodeClicked = { episodeId -> onEpisodeClicked(episodeId) }
            ).id(it.id)
        }

        if (character!!.episodesList.isNotEmpty()) {
            TitleEpoxyModel("Episodes").id("title_episodes").addTo(this)
            CarouselModel_()
                .id("episode_carousel")
                .models(items)
                .numViewsToShowOnScreen(1.2f)
                .addTo(this)
        }

        DataPointEpoxyModel(
            title = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            title = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)

    }

    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            fullNameView.text = name
            if (gender.equals("male", true)) {
                genderView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderView.setImageResource(R.drawable.ic_female_24)
            }
            if (status.equals("Alive", true)) {
                statusView.setImageResource(R.drawable.ic_status_alive)
            } else if (status.equals("Dead", true)) {
                statusView.setImageResource(R.drawable.ic_status_dead)
            } else {
                statusView.setImageResource(R.drawable.ic_status_unknown)
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

    data class EpisodeCarouselItem(
        val episode: EpisodeModel,
        val onEpisodeClicked: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(
        R.layout.model_episode_carousel_item
    ) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodeTextView.text = episode.getFormattedSeasonTruncated()
            episodeDetailsTextView.text = "${episode.name}\n${episode.airDate}"
            root.setOnClickListener {
                onEpisodeClicked(episode.id)
            }
        }
    }

    data class TitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelTitleBinding>(R.layout.model_title) {

        override fun ModelTitleBinding.bind() {
            titleTextView.text = title
        }
    }
}