package com.example.rickandmortyapp

import android.app.Application
import android.content.Context

class RickAndMortyApp : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}
