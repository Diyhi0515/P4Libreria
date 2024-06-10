package com.example.p4libreria.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.LibroGeneros
import com.example.p4libreria.models.Libros
import com.example.p4libreria.repositories.GeneroRepository
import com.example.p4libreria.repositories.LibroGeneroRepository
import com.example.p4libreria.repositories.LibroRepository

class LibroGeneroViewModel : ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    private val _libroGenero: MutableLiveData<LibroGeneros?> by lazy {
        MutableLiveData<LibroGeneros?>(null)
    }
    val libroGenero: LiveData<LibroGeneros?> get() = _libroGenero

    fun insertBookGenders(libroId: Int, generoId: Int) {
        val libroGeneros = LibroGeneros(libro_id = libroId, genero_id = generoId)
        LibroGeneroRepository.insertBookGenders(libroGeneros,
            success = {
                _libroGenero.value = it
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    fun deleteBookGenres(libroId: Int, generoId: Int) {
        val libroGeneros = LibroGeneros(libro_id = libroId, genero_id = generoId)
        LibroGeneroRepository.deleteBookGenres(libroGeneros,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    private val _librito: MutableLiveData<Libro?> by lazy {
        MutableLiveData<Libro?>(null)
    }
    val librito: LiveData<Libro?> get() = _librito

    fun buscarLibro(id: Int) {
        LibroRepository.getBook(id,
            success = {
                _librito.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }

    private val _genero: MutableLiveData<Genero?> by lazy {
        MutableLiveData<Genero?>(null)
    }
    val genero: LiveData<Genero?> get() = _genero

    fun buscarGenero(id: Int) {
        GeneroRepository.getGenre(id,
            success = {
                _genero.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }
    private val _librosList: MutableLiveData<List<Libro>> by lazy {
        MutableLiveData<List<Libro>>(emptyList())
    }
    val librosList: LiveData<List<Libro>> get() = _librosList

    fun cargarLibros() {
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
    private val _libroG: MutableLiveData<List<Libro>> by lazy {
        MutableLiveData<List<Libro>>(emptyList())
    }
    val librosG: LiveData<List<Libro>> get() = _libroG

    fun getBooksByGenre(generoId: Int) {
        val libros = _librosList.value ?: emptyList()
        val filteredBooks = libros.filter { libro ->
            libro.generos.any { genero -> genero.id == generoId }
        }
        _libroG.value = filteredBooks
    }

}

