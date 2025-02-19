package com.example.cinefy.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.Response

object RetrofitInstance {
    private const val BASE_URL = "https://imdb-top-100-movies.p.rapidapi.com/"
    private const val API_KEY = "3b4bc50058msh5f391f156e812e6p16fdd0jsn7949097109f6"  // Reemplaza con tu token de autenticación

    // Crear un interceptor para agregar el token en las cabeceras
    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $API_KEY")  // Agregar el token en la cabecera
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val api: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // Usar el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }
}
