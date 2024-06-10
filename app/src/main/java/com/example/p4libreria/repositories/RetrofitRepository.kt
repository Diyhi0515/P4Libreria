package com.example.p4libreria.repositories

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitRepository {
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apilibreria.jmacboy.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}