package com.example.cinefy.repository

import MovieApiService
import com.example.cinefy.ui.model.Movie
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository(private var api: MovieApiService) {

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://imdb-top-100-movies.p.rapidapi.com/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieApiService::class.java)
    }

    suspend fun getMovies(): List<Movie> {
        return try {
            api.getMovies()
        } catch (e: HttpException) {
            // Manejo de errores
            emptyList() // O maneja el error de otra manera
        }
    }
}