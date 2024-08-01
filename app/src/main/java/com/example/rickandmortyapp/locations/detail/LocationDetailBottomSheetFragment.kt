package com.example.rickandmortyapp.locations.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.rickandmortyapp.databinding.FragmentLocationDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LocationDetailBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentLocationDetailBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel:LocationDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locationLiveData.observe(viewLifecycleOwner) { location ->

            if (location == null) {
                // todo handle error
                return@observe
            }

            binding.locationNameTextView.text = location.name
            binding.locationTypeTextView.text = location.type

            binding.epoxyRecyclerView.setControllerAndBuildModels(
                LocationDetailEpoxyController(location.residentsList)
            )
        }

        viewModel.fetchLocation(locationId = LocationDetailBottomSheetFragmentArgs.fromBundle(requireArguments()).locationId)
    }

}