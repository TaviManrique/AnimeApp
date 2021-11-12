package com.manriquetavi.animeapp.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manriquetavi.animeapp.common.entities.AnimeEntity
import com.manriquetavi.animeapp.mainModule.MainInteractor

class MainViewModel: ViewModel() {
    private var animeList: MutableList<AnimeEntity>
    private var interactor: MainInteractor


    init {
        animeList = mutableListOf()
        interactor = MainInteractor()
    }
    private val animes: MutableLiveData<MutableList<AnimeEntity>> by lazy {
        MutableLiveData<MutableList<AnimeEntity>>().also {
            loadAnimes()
        }
    }

    private fun loadAnimes() {
        interactor.getAnimes {
            animes.value = it
            animeList = it
        }
    }

    fun getAnimes(): LiveData<MutableList<AnimeEntity>> {
        return animes
    }
}