package com.example.rickandmortyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.characters.CharactersListPagingEpoxyController
import com.example.rickandmortyapp.characters.CharactersViewModel

class CharacterListFragment : Fragment() {


    private val epoxyController = CharactersListPagingEpoxyController(::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.charactersPagedList.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
            .setController(epoxyController)

    }

    private fun onCharacterSelected(characterId: Int) {
        val directions = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                characterId = characterId
            )
        findNavController().navigate(directions)
    }


}