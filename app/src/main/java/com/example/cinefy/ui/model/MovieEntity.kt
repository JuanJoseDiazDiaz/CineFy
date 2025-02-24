package com.example.cinefy.ui.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val rank: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "bigImageUrl")
    val bigImageUrl: String,
    @ColumnInfo(name = "genres")
    val genres: String,
    @ColumnInfo(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "imdbId")
    val imdbId: String,
    @ColumnInfo(name = "imdbLink")
    val imdbLink: String,
)