package com.example.rickandmortyapp.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortyapp.databinding.ModelEpisodeListTitleBinding
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.epoxy.LoadingEpoxyModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel

class EpisodesListEpoxyController(
    private val onEpisodeClicked: (Int) -> Unit
) : PagingDataEpoxyController<EpisodeUiModel>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: EpisodeUiModel?
    ): EpoxyModel<*> {
        return when(item!!) {
            is EpisodeUiModel.Item -> {
                val episode = (item as EpisodeUiModel.Item).episode
                EpisodeListItemEpoxyModel(
                    episode = episode,
                    onClick = { episodeId ->
                        onEpisodeClicked(episodeId)
                    }
                ).id("episode_$episode.id")
            }

            is EpisodeUiModel.Header -> {
                val headerText = (item as EpisodeUiModel.Header).text
                EpisodeListTitleEpoxyModel(headerText).id(headerText)
            }
        }
    }

    data class EpisodeListItemEpoxyModel(
        val episode: EpisodeModel,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {

        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.getFormattedSeasonTruncated()

            root.setOnClickListener {onClick(episode.id)}
        }
    }

    data class EpisodeListTitleEpoxyModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelEpisodeListTitleBinding>(R.layout.model_episode_list_title) {

        override fun ModelEpisodeListTitleBinding.bind() {
            textView.text = title
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }
        super.addModels(models)
    }

}