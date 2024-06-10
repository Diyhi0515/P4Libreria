package com.example.p4libreria.repositories

import com.example.p4libreria.api.APILibrosService
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.LibroGeneros

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LibroGeneroRepository {
    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service: APILibrosService = retrofit.create(APILibrosService::class.java)

    fun insertBookGenders(
        libroGeneros: LibroGeneros,
        success: (LibroGeneros) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        service.insertLibroGeneros(libroGeneros).enqueue(object : Callback<LibroGeneros> {
            override fun onResponse(res: Call<LibroGeneros>, response: Response<LibroGeneros>) {
                val objLibroGeneros = response.body()
                if (objLibroGeneros != null) {
                    success(objLibroGeneros)
                } else {
                    failure(Throwable("Failed to insert libro-generos"))
                }
            }

            override fun onFailure(res: Call<LibroGeneros>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteBookGenres(
        libroGeneros: LibroGeneros,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        service.deleteLibroGeneros(libroGeneros).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Throwable("Failed to delete libro-generos"))
                }
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }


}
