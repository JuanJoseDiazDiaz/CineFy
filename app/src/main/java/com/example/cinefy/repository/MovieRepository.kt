package com.example.cinefy.repository

import com.example.cinefy.data.MovieApiService
import com.example.cinefy.ui.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository(api: MovieApiService) {
    private val api: MovieApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://imdb-top-100-movies.p.rapidapi.com/") // URL base de la API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.api = retrofit.create(MovieApiService::class.java)
    }

    suspend fun getMovies(): List<Movie> {
        return api.getMovies()
    }
}