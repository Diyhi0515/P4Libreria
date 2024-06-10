package com.example.p4libreria.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Generos
import com.example.p4libreria.repositories.GeneroRepository
import com.example.p4libreria.repositories.LibroRepository

class GenerosViewModel: ViewModel(){
    private val _generosList: MutableLiveData<Generos> by lazy {
        MutableLiveData<Generos>(Generos())
    }
    val generosList: LiveData<Generos> get() = _generosList

    fun buscarGeneros() {
        Log.d("MainViewModel", "buscandoListaPersonas")
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