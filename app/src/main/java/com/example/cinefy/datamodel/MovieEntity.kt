package com.example.cinefy.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cinefy.localdatabase.Converters

@Entity(tableName = "movies_favorites")
@TypeConverters(Converters::class)
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
    val genres: List<String>,
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

    // Esta varibales gestion acciones de la aplicacion
    val isFavorite: Boolean = false,
    val comments: List<Comment> = emptyList()// Lista de comentarios
)
fun Movie.toMovieEntity(isFavorite: Boolean = false): MovieEntity {
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
        isFavorite = isFavorite,
        comments = emptyList() // Añadir comentarios
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

