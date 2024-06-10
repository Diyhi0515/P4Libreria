package com.example.p4libreria.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Genero
import com.example.p4libreria.repositories.GeneroRepository

class GeneroDetailViewModel:ViewModel(){
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
    private val _genero: MutableLiveData<Genero?> by lazy {
        MutableLiveData<Genero?>(null)
    }
    val genero: LiveData<Genero?> get() = _genero

    fun saveGenero(nombre: String, id: Int) {
        val genero = Genero(
            nombre = nombre
        )
        GeneroRepository.insertGenre(genero,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

    fun buscarGenero(id: Int) {
        GeneroRepository.getGenre(id,
            success = {
                _genero.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }
}