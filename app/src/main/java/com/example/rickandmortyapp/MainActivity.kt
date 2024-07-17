package com.example.rickandmortyapp

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val nameView = findViewById<TextView>(R.id.full_name)
        val genderView = findViewById<ImageView>(R.id.gender)
        val headerImageView = findViewById<ImageView>(R.id.header_image)
        val originView = findViewById<TextView>(R.id.origin)
        val speciesView = findViewById<TextView>(R.id.species)

        val service: RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
        service.getCharacterById(9).enqueue(object : Callback<GetCharacterByIdResponse> {
            override fun onResponse(call: Call<GetCharacterByIdResponse>, response: Response<GetCharacterByIdResponse>) {
                Log.i("MainActivity", "Response: $response")

                if (!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Unsuccessfull network call", Toast.LENGTH_SHORT)
                    return
                }

                val body = response.body()!!

                nameView.text = body.name
                originView.text = body.origin.name
                speciesView.text = body.species

                if (body.gender.equals("Male", true)) {
                    genderView.setImageResource(R.drawable.ic_male_24)
                } else {
                    genderView.setImageResource(R.drawable.ic_female_24)
                }


                Picasso.get().load(body.image).into(headerImageView)

            }

            override fun onFailure(call: Call<GetCharacterByIdResponse>, t: Throwable) {
                Log.i("MainActivity", t.message ?: "Null message")
            }

        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}