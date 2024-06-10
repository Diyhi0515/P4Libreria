package com.example.p4libreria.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Generos
import com.example.p4libreria.repositories.GeneroRepository

class GeneroAlibro: ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val closeActivity: LiveData<Boolean> get() = _closeActivity


    private val _generosList: MutableLiveData<Generos> by lazy {
        MutableLiveData<Generos>(Generos())
    }
    val generosList: LiveData<Generos> get() = _generosList



    fun buscarListaGeneros() {
        GeneroRepository.getGenreList(
            success = { generos ->
                generos?.let {
                    _generosList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            })
    }
}