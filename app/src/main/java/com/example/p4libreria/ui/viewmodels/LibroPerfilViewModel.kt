package com.example.p4libreria.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.p4libreria.models.Libro
import com.example.p4libreria.repositories.LibroRepository

class LibroPerfilViewModel: ViewModel(){
    private val _closeActivity: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity: LiveData<Boolean> get() = _closeActivity
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

    fun deleteLibro(id: Int) {
        LibroRepository.deleteBook(id,
            success = {
                _closeActivity.value = true
            },
            failure = {
                it.printStackTrace()
            })
    }

}