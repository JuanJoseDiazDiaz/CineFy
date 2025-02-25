package com.example.cinefy.ui.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("rank") val rank: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("big_image") val bigImageUrl: String,
    @SerializedName("genre") val genres: List<String>,
    @SerializedName("thumbnail") val thumbnailUrl: String,
    @SerializedName("rating") val rating: Float,
    @SerializedName("id") val id: String,
    @SerializedName("year") val year: Int,
    @SerializedName("imdbid") val imdbId: String,
    @SerializedName("imdb_link") val imdbLink: String,
)
fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        rank = this.rank,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        bigImageUrl = this.bigImageUrl,
        genres = this.genres,
        thumbnailUrl = this.thumbnailUrl,
        rating = this.rating,
        id = this.id,
        year = this.year,
        imdbId = this.imdbId,
        imdbLink = this.imdbLink,
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        rank = this.rank,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl,
        bigImageUrl = this.bigImageUrl,
        genres = this.genres,
        thumbnailUrl = this.thumbnailUrl,
        rating = this.rating,
        id = this.id,
        year = this.year,
        imdbId = this.imdbId,
        imdbLink = this.imdbLink,
    )
}

