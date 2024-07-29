package com.example.rickandmortyapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView

class CharacterDetailActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val epoxyController = CharacterDetailsEpoxyController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.characterById.observe(this) { character ->
            epoxyController.character = character
            if (character == null) {
                Toast.makeText(this, "Unsuccessfull network call", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }
        viewModel.refreshCharacter(characterId = intent.getIntExtra(Constants.INTENT_EXTRA_CHARACTER_ID, 1))

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}