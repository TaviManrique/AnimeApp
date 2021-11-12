package com.manriquetavi.animeapp.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.progressindicator.AnimatorDurationScaleProvider
import com.manriquetavi.animeapp.common.entities.AnimeEntity
import com.manriquetavi.animeapp.mainModule.MainInteractor

class MainViewModel: ViewModel() {
    //Mejores practicas
    /*val animeList: MutableLiveData<MutableList<AnimeEntity>> by lazy {
        MutableLiveData<MutableList<AnimeEntity>>()
    }*/
    private val animeList = mutableListOf<AnimeEntity>()
    private var interactor: MainInteractor

    init {
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
            animeList.addAll(it)
        }
    }

    fun getAnimes(): LiveData<MutableList<AnimeEntity>> {
        return animes
    }
}