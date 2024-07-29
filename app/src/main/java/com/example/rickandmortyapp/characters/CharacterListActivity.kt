package com.example.rickandmortyapp.characters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickandmortyapp.Constants
import com.example.rickandmortyapp.CharacterDetailActivity
import com.example.rickandmortyapp.R

class CharacterListActivity: AppCompatActivity() {

    private val epoxyController = CharactersListPagingEpoxyController(::onCharacterSelected)

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        viewModel.charactersPagedList.observe(this) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view).setController(epoxyController)
    }

    private fun onCharacterSelected(characterId: Int) {
        val intent = Intent(this, CharacterDetailActivity::class.java)
        intent.putExtra(Constants.INTENT_EXTRA_CHARACTER_ID, characterId)
        startActivity(intent)
    }
}