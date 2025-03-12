package com.example.cinefy.repository

import android.util.Log
import com.example.cinefy.api.MovieApiService
import com.example.cinefy.datamodel.Movie

class MovieRepository(private val api: MovieApiService) {
    suspend fun getMovies(): List<Movie> {
        return try {
            val response = api.getMovies()
            Log.d("Repository", "Movies received: $response") // 📌 Verifica que llegan datos
            response
        } catch (e: Exception) {
            Log.e("Repository", "Error fetching movies: ${e.message}")
            emptyList()
        }
    }
}