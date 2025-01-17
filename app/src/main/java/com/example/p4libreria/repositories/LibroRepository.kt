package com.example.p4libreria.repositories

import android.util.Log
import com.example.p4libreria.api.APILibrosService
import com.example.p4libreria.models.Libro
import com.example.p4libreria.models.Libros
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LibroRepository {
    fun getBookList(success: (Libros?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.getLibros().enqueue(object : Callback<Libros> {
            override fun onResponse(res: Call<Libros>, response: Response<Libros>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Libros>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertBook(libro: Libro, success: (Libro) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)

        service.insertLibro(libro).enqueue(object : Callback<Libro> {
            override fun onResponse(res: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    success(response.body()!!)
                } else {
                    failure(Throwable("Error en la respuesta: ${response.errorBody()?.string()}"))
                }
            }

            override fun onFailure(res: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getBook(id: Int, success: (Libro?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.getLibroById(id).enqueue(object : Callback<Libro?> {
            override fun onResponse(res: Call<Libro?>, response: Response<Libro?>) {
                success(response.body())
            }

            override fun onFailure(res: Call<Libro?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateBook(
        libro: Libro,
        id: Int,
        success: (Libro) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        val libroId = libro.id ?: 0
        service.updateLibro(libro, id).enqueue(object : Callback<Libro> {
            override fun onResponse(res: Call<Libro>, response: Response<Libro>) {
                val objlibro = response.body()!!
                Log.d("LibroActualizado", libro.toString())
                success(objlibro)
            }

            override fun onFailure(res: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteBook(
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APILibrosService =
            retrofit.create(APILibrosService::class.java)
        service.deleteLibro(id).enqueue(object : Callback<Void> {
            override fun onResponse(res: Call<Void>, response: Response<Void>) {
                success()
            }

            override fun onFailure(res: Call<Void>, t: Throwable) {
                failure(t)
            }
        })
    }
}