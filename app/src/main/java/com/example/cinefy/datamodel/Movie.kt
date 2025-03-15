package com.example.cinefy.datamodel

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
    @Transient val isFavorite: Boolean = false,
)


