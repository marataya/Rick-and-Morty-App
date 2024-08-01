package com.example.rickandmortyapp.episodes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentEpisodeListBinding
import com.example.rickandmortyapp.domain.models.EpisodeModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeListFragment : Fragment() {

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

        val epoxyController = EpisodesListEpoxyController()

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                Log.i("Epoxy", "pagingData: $pagingData")
                epoxyController.submitData(pagingData)
            }
        }
        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}