package com.example.rickandmortyapp.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmortyapp.databinding.FragmentLocationListBinding
import com.example.rickandmortyapp.domain.models.LocationModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [LocationsListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationsListFragment : Fragment() {

    private var _binding: FragmentLocationListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: LocationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = LocationsListEpoxyController(::onLocationSelected)

        val swipeRefreshLayout = binding.swipeLayout
        swipeRefreshLayout.setOnRefreshListener {
            // Your code to refresh the content
            epoxyController.requestModelBuild()

            // Once the refresh is complete, call setRefreshing(false)
            swipeRefreshLayout.isRefreshing = false
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<LocationModel> ->
                epoxyController.submitData(pagingData)
            }
        }
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun onLocationSelected(locationId: Int) {
        val directions = LocationsListFragmentDirections.actionLocationsListFragmentToLocationDetailBottomSheetFragment(locationId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}