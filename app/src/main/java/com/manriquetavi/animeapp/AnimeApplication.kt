package com.manriquetavi.animeapp

import android.app.Application
import com.manriquetavi.animeapp.common.api.AnimeAPI

class AnimeApplication: Application() {
    companion object{
        lateinit var animeAPI: AnimeAPI
    }

    override fun onCreate() {
        super.onCreate()

        animeAPI = AnimeAPI.getInstance(this)
    }
}