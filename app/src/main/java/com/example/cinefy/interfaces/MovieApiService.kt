package com.example.cinefy.interfaces

import com.example.cinefy.ui.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiService {
    @GET("/")
    suspend fun getMovies(): List<Movie>

}


