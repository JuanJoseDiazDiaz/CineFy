package com.example.cinefy.data

import com.example.cinefy.ui.model.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("/") // Solo el endpoint relativo
    suspend fun getMovies(): List<Movie>
}