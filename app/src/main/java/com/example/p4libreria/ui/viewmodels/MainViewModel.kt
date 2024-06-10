package com.example.p4libreria.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Libros
import com.example.p4libreria.repositories.LibroRepository

class MainViewModel: ViewModel() {
    private val _librosList: MutableLiveData<Libros> by lazy {
        MutableLiveData<Libros>(Libros())
    }
    val librosList: LiveData<Libros> get() = _librosList

    fun buscarLibros() {
        Log.d("MainViewModel", "buscandoListaPersonas")
        LibroRepository.getBookList(
            success = { libros ->
                libros?.let {
                    val sortedLibros = it.sortedByDescending { libro -> libro.calificacion }
                    val sortedLibrosList = Libros()
                    sortedLibrosList.addAll(sortedLibros)
                    _librosList.value = sortedLibrosList
                }
            },
            failure = {
                it.printStackTrace()
            })
        if (_librosList.value != null) {
            Log.d("Libros", _librosList.toString())
        }
    }
}