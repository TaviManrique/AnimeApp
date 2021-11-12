package com.manriquetavi.animeapp.bioModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manriquetavi.animeapp.bioModule.model.BioFragmentInteractor
import com.manriquetavi.animeapp.common.entities.AnimeEntity

class BioFragmentViewModel: ViewModel() {
    private val animeSelected = MutableLiveData<AnimeEntity>()
    private val result = MutableLiveData<Any>()

    private val interactor: BioFragmentInteractor

    init{
        interactor = BioFragmentInteractor()
    }

    fun setAnimeSelected(animeEntity: AnimeEntity){
        animeSelected.value = animeEntity
    }

    fun getAnimeSelected(): LiveData<AnimeEntity>{
        return animeSelected
    }

    fun getResult(): LiveData<Any>{
        return result
    }

}