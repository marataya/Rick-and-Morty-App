package com.example.rickandmortyapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val nameView = findViewById<TextView>(R.id.full_name)
        val genderView = findViewById<ImageView>(R.id.gender)
        val headerImageView = findViewById<ImageView>(R.id.header_image)
        val originView = findViewById<TextView>(R.id.origin)
        val speciesView = findViewById<TextView>(R.id.species)

        viewModel.refreshData(12)
        viewModel.characterById.observe(this) { data ->
            if (data == null) {
                Toast.makeText(this, "Unsuccessfull network call", Toast.LENGTH_SHORT).show()
                return@observe
            }
            nameView.text = data.name
            originView.text = data.origin.name
            speciesView.text = data.species
            if (data.gender.equals("Male", true)) {
                genderView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderView.setImageResource(R.drawable.ic_female_24)
            }

            Picasso.get().load(data.image).into(headerImageView)
        }


    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
    {
        v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }
}
}