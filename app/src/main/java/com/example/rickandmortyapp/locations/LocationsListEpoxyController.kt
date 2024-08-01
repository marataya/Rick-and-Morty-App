package com.example.rickandmortyapp.locations

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.ModelLocationListItemBinding
import com.example.rickandmortyapp.domain.models.LocationModel
import com.example.rickandmortyapp.epoxy.ViewBindingKotlinModel

class LocationsListEpoxyController(
    private val onLocationClicked: (Int) -> Unit
) : PagingDataEpoxyController<LocationModel>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: LocationModel?
    ): EpoxyModel<*> {
        return LocationListItemEpoxyModel(
            location = item!!,
            onClick = { locationId ->
                onLocationClicked(locationId)
            }
        ).id("location_${item.id}")

    }

    data class LocationListItemEpoxyModel(
        val location: LocationModel,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelLocationListItemBinding>(R.layout.model_location_list_item) {

        override fun ModelLocationListItemBinding.bind() {
            locationNameTextView.text = location.name
            locationTypeTextView.text = location.type
            locationCreatedTextView.text = location.created
            root.setOnClickListener { onClick(location.id) }
        }
    }

}