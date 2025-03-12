package com.example.cinefy.api

import com.example.cinefy.datamodel.Movie
import retrofit2.http.GET

interface MovieApiService {
    @GET("/")
    suspend fun getMovies(): List<Movie>

}


