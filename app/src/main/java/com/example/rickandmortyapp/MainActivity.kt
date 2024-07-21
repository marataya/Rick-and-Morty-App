package com.example.rickandmortyapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val epoxyController = CharacterDetailsEpoxyController()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        viewModel.characterById.observe(this) { data ->
            epoxyController.characterResponse = data
            if (data == null) {
                Toast.makeText(this, "Unsuccessfull network call", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }
        viewModel.refreshCharacter(12)

        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
        { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}