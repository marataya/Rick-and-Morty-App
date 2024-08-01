package com.example.rickandmortyapp.locations.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.domain.models.LocationModel
import com.example.rickandmortyapp.locations.LocationRepo
import kotlinx.coroutines.launch

class LocationDetailViewModel : ViewModel() {
    private val repo = LocationRepo()

    private var _locationLiveData = MutableLiveData<LocationModel?>()
    val locationLiveData: LiveData<LocationModel?> = _locationLiveData

    fun fetchLocation(locationId: Int) {
        viewModelScope.launch {
            val location = repo.getLocationById(locationId)
            _locationLiveData.postValue(location)
        }
    }
}