package com.example.p4libreria.models
typealias Generos = ArrayList<Genero>

data class Genero(
    val nombre: String,
){
    var id: Int? = null
    var libros: List<Libro> = emptyList()
}
