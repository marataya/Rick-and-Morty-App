package com.example.rickandmortyapp.episodes

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ModelEpisodeListItemBinding
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.epoxy.LoadingEpoxyModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel

class EpisodesListEpoxyController : PagingDataEpoxyController<EpisodeModel>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: EpisodeModel?
    ): EpoxyModel<*> {
        Log.i("epoxyBind", "BuildItemModel")
        return EpisodeListItemEpoxyModel(
            episode = item!!,
            onClick = { episodeId ->
                //TODO
            }
        ).id("episode_${item.id}")
    }

    data class EpisodeListItemEpoxyModel(
        val episode: EpisodeModel,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeListItemBinding>(R.layout.model_episode_list_item) {
        override fun ModelEpisodeListItemBinding.bind() {
            episodeNameTextView.text = episode.name
            episodeAirDateTextView.text = episode.airDate
            episodeNumberTextView.text = episode.episode
        }

    }

}