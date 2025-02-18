package com.example.cinefy.data

import com.example.cinefy.ui.model.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("https://imdb-top-100-movies.p.rapidapi.com/") // Reemplaza con la URL correcta de tu API
    suspend fun getMovies(): List<Movie>
}