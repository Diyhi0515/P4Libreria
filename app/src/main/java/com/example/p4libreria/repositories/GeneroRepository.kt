package com.example.p4libreria.repositories

import android.util.Log
import com.example.p4libreria.api.APILibrosService
import com.example.p4libreria.models.Genero
import com.example.p4libreria.models.Generos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GeneroRepository {
    fun getGenreList(success: (Generos?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.getGeneros().enqueue(object : Callback<Generos> {
            override fun onResponse(res: Call<Generos>, response: Response<Generos>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Generos>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertGenre(
        genero: Genero,
        success: (Genero) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.insertGenero(genero).enqueue(object : Callback<Genero> {
            override fun onResponse(res: Call<Genero>, response: Response<Genero>) {
                val objgenero = response.body()
                Log.d("GeneroInsertado", genero.toString())
                success(objgenero!!)
            }

            override fun onFailure(res: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getGenre(id: Int, success: (Genero?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.getGeneroById(id).enqueue(object : Callback<Genero?> {
            override fun onResponse(res: Call<Genero?>, response: Response<Genero?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Genero?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateGenre(
        genero: Genero,
        success: (Genero) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        val generoId = genero.id ?: 0
        service.updateGenero(genero, generoId).enqueue(object : Callback<Genero> {
            override fun onResponse(res: Call<Genero>, response: Response<Genero>) {
                val objgenero = response.body()!!
                Log.d("GeneroActualizado", genero.toString())
                success(objgenero)
            }

            override fun onFailure(res: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteGenre(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.deleteGenero(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}