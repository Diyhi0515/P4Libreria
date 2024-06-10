package com.example.p4libreria.api

import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Generos
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.LibroGeneros
import com.example.p4libreria.models.Libros
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APILibrosService {
    @GET("libros")
    fun getLibros(): Call<Libros>

    @GET("libros/{id}")
    fun getLibroById(
        @Path("id") id: Int
    ): Call<Libro?>

    @POST("libros")
    fun insertLibro(
        @Body libro: Libro
    ): Call<Libro>

    @PUT("libros/{id}")
    fun updateLibro(
        @Body libro: Libro,
        @Path("id") id: Int
    ): Call<Libro>

    @DELETE("libros/{id}")
    fun deleteLibro(
        @Path("id") id: Int
    ): Call<Void>


    @GET("generos")
    fun getGeneros(): Call<Generos>

    @GET("generos/{id}")
    fun getGeneroById(
        @Path("id") id: Int
    ): Call<Genero?>

    @POST("generos")
    fun insertGenero(
        @Body genero: Genero
    ): Call<Genero>

    @PUT("generos/{id}")
    fun updateGenero(
        @Body genero: Genero,
        @Path("id") id: Int
    ): Call<Genero>

    @DELETE("generos/{id}")
    fun deleteGenero(
        @Path("id") id: Int
    ): Call<Void>

    @POST("libro-generos")
    fun insertLibroGeneros(
        @Body libroGeneros: LibroGeneros
    ): Call<LibroGeneros>

    @HTTP(method = "DELETE", path = "libro-generos", hasBody = true)
    fun deleteLibroGeneros(
        @Body libroGeneros: LibroGeneros
    ): Call<Void>
}