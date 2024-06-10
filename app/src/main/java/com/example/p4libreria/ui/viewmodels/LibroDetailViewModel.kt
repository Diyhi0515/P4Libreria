package com.example.p4libreria.ui.viewmodels

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.LibroGeneros
import com.example.p4libreria.models.Libros
import com.example.p4libreria.repositories.LibroGeneroRepository
import com.example.p4libreria.repositories.LibroRepository
import com.example.p4libreria.ui.activities.LibroDetailActivity
import com.example.p4libreria.ui.activities.LibroPerfilActivity
import java.util.Locale.Category

class LibroDetailViewModel: ViewModel() {
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity

    private val _libro: MutableLiveData<Libro?> by lazy {
        MutableLiveData<Libro?>(null)
    }
    val libro: LiveData<Libro?> get() = _libro

    private val _editarGeneros: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val editarGeneros: LiveData<Boolean> get() = _editarGeneros

    private val _librosList: MutableLiveData<Libros> by lazy {
        MutableLiveData<Libros>(null)
    }
    val librosList: LiveData<Libros> get() = _librosList

    fun saveLibro(nombre: String, autor: String, editorial: String, isbn: String, imagen: String, sinopsis: String, calificacion: Int, id: Int) {
        val libro = Libro(
            nombre = nombre, autor = autor, editorial = editorial,
            imagen = imagen, sinopsis = sinopsis, isbn = isbn, calificacion = calificacion
        )
        if (id != -1) {
            libro.id = id
            LibroRepository.updateBook(libro,id,
                success = {
                    _editarGeneros.value = true
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        } else {
            LibroRepository.insertBook(libro,
                success = {
                    _editarGeneros.value = true
                    _closeActivity.value = true
                },
                failure = {
                    it.printStackTrace()
                })
        }
    }

    fun buscarLibro(id: Int) {
        LibroRepository.getBook(id,
            success = {
                _libro.value = it
            },
            failure = {
                it.printStackTrace()
            })
    }




    fun editarGeneros(
        generosId : ArrayList<Int>,
        generosNew : ArrayList<Int>? = null,
        id : Int
    )
    {
        if (generosNew != null){
            for (generoId in generosId) {
                if (!generosNew.contains(generoId)) {
                    eliminarGeneroLibro(generoId, id)
                }
            }

            for (generoId in generosNew) {
                if (!generosId.contains(generoId)) {
                    agregarGeneroLibro(generoId, id)
                }
            }
        }
        _closeActivity.value = true
    }

    fun agregarGeneroLibro(
        generoId: Int,
        libroId: Int
    ){
        val generoLibro = LibroGeneros(
            genero_id = generoId,
            libro_id = libroId
        )

        LibroGeneroRepository.insertBookGenders(
            generoLibro,
            success = {
                Log.d("LibroSaveViewModel", "Genero insertado")
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    fun eliminarGeneroLibro(
        generoId: Int,
        libroId: Int
    ){
        val generoLibro = LibroGeneros(
            genero_id = generoId,
            libro_id = libroId
        )

        LibroGeneroRepository.deleteBookGenres(
            generoLibro,
            success = {
                Log.d("LibroSaveViewModel", "Genero eliminado")
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    fun getLastId(){
        LibroRepository.getBookList(
            success = {
                _librosList.value = it
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

}

