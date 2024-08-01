package com.example.rickandmortyapp.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.databinding.FragmentCharacterListBinding

class CharactersListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding
        get() = _binding!!

    private val epoxyController = CharactersListPagingEpoxyController(::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.charactersPagedList.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view).setController(epoxyController)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCharacterSelected(characterId: Int) {
        val directions = CharactersListFragmentDirections.actionCharacterListFragmentToCharacterDetailsFragment(
                characterId = characterId
            )
        findNavController().navigate(directions)
    }


}