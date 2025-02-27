package com.example.cinefy.data

import com.example.cinefy.interfaces.MovieApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val API_KEY = "3b4bc50058msh5f391f156e812e6p16fdd0jsn7949097109f6"
    private const val API_HOST = "imdb-top-100-movies.p.rapidapi.com"
    private const val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com/"
    private const val MAX_RETRIES = 3
    private const val INITIAL_DELAY = 2L

    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-rapidapi-key", API_KEY)
            .addHeader("x-rapidapi-host", API_HOST)
            .build()

        val response = chain.proceed(request)

        println("➡️ Request: ${request.url} | Headers: ${request.headers}")
        println("⬅️ Response: ${response.code} - ${response.message}")

        response
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val api: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // Usando solo GsonConverterFactory
            .build()
            .create(MovieApiService::class.java)
    }
}
