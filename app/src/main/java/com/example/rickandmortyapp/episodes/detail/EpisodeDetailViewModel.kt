package com.example.rickandmortyapp.episodes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.domain.models.EpisodeModel
import com.example.rickandmortyapp.episodes.EpisodeRepo
import kotlinx.coroutines.launch

class EpisodeDetailViewModel : ViewModel() {
    private val repo = EpisodeRepo()

    private var _episodeLiveData = MutableLiveData<EpisodeModel?>()
    val episodeLiveData: LiveData<EpisodeModel?> = _episodeLiveData

    fun fetchEpisode(episodeId: Int) {
        viewModelScope.launch {
            val episode = repo.getEpisodeById(episodeId)
            _episodeLiveData.postValue(episode)
        }
    }
}