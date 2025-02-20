package com.example.cinefy.data

import MovieApiService
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
        var response: Response
        var attempt = 0
        val maxRetries = 3 // Número máximo de reintentos

        while (true) {
            // Crear la solicitud con el token de autorización
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()

            response = chain.proceed(request)

            try {
                if (response.code == 429 && attempt < maxRetries) {
                    val retryAfter = response.header("Retry-After")?.toLongOrNull() ?: 2
                    Thread.sleep(retryAfter * 1000)
                    attempt++
                    continue  // Si es 429, reintenta la solicitud
                }
                break  // Si no es 429 o no hay más reintentos, salimos del ciclo
            } finally {
                response.close()  // Cerramos la respuesta al finalizar el intento
            }
        }

        response  // Retornamos la última respuesta (exitosa o no)
    }


    // Crear el cliente OkHttp con el interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)  // Añadir el interceptor para manejar el error 429
        .build()

    // Crear la instancia Retrofit para hacer las solicitudes a la API
    val api: MovieApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)  // Usar el cliente con el interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }
}
