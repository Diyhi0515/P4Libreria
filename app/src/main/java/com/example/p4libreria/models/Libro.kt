package com.example.p4libreria.models

typealias Libros = ArrayList<Libro>

data class Libro(
    val nombre: String,
    val autor: String,
    val editorial: String,
    val imagen: String,
    val sinopsis: String,
    val isbn: String,
    val calificacion: Int
){
    var id: Int? = null
    var generos: List<Genero> = emptyList()
}
