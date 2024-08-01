package com.example.rickandmortyapp.episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmortyapp.databinding.FragmentEpisodeListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodesListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodesListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: EpisodesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = EpisodesListEpoxyController(::onEpisodeSelected)

        val swipeRefreshLayout = binding.swipeLayout
        swipeRefreshLayout.setOnRefreshListener {
            // Your code to refresh the content
            epoxyController.requestModelBuild()

            // Once the refresh is complete, call setRefreshing(false)
            swipeRefreshLayout.isRefreshing = false
        }

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                epoxyController.submitData(pagingData)
            }
        }
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    private fun onEpisodeSelected(episodeId: Int) {
        val directions = EpisodesListFragmentDirections.actionEpisodeListFragmentToEpisodeDetailBottomSheetFragment(episodeId)
        findNavController().navigate(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}