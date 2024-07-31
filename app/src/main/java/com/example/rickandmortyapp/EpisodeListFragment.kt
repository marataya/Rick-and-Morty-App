package com.example.rickandmortyapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmortyapp.databinding.FragmentEpisodeListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding: FragmentEpisodeListBinding?
        get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEpisodeListBinding.bind(view)
    }

}