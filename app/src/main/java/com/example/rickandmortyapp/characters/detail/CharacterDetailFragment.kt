package com.example.rickandmortyapp.characters.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.R


class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
    }

    private val epoxyController = CharacterDetailEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.characterById.observe(viewLifecycleOwner) { character ->

            epoxyController.character = character

            if (character == null) {
                Toast.makeText(
                    requireActivity(),
                    "Unsuccessful network call!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigateUp()
                return@observe
            }
        }

        viewModel.fetchCharacter(characterId = CharacterDetailFragmentArgs.fromBundle(requireArguments()).characterId)

        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

}