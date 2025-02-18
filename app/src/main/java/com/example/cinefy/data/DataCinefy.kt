package com.example.cinefy.data

import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie

object DataCinefy {
    val movieList: () -> MutableList<Movie> = {
        mutableListOf<Movie>(
            Movie(
                rank = 32,
                title = "OppenHeimer",,,,,,,
                id = "top32",,,
                imdbLink = "https://www.imdb.com/title/tt15398776"
            ),
            Movie(
                rank = 33,
                title = "Harakiri",,,,,,,
                id = "top33",,,
                imdbLink = "https://www.imdb.com/title/tt0056058"
            ),
            Movie(
                rank = 34,
                title = "Back to the Future",,,,,,,
                id = "top34",,,
                imdbLink = "https://www.imdb.com/title/tt0088763"
            ),
            Movie(
                rank = 35,
                title = "The Pianist",,,,,,,
                id = "top35",,,
                imdbLink = "https://www.imdb.com/title/tt0253474"
            )
        ).apply { shuffle()}
    }
    val getListXtimes : (Int) -> MutableList<Movie> = { times ->
        val list = mutableListOf<Movie>()
        for (i in 1..times){
            list.addAll(movieList())
        }
        list.shuffle()
        list
    }
    val getMovieName : (String) -> Movie? = { title ->
        movieList().find { it.title == title } ?: null
    }

    val getSomeRandMovies : (Int) -> MutableList<Movie> = { num ->
        val movies = movieList()
        if(num <= movies.size) movies.subList(0, num)
        movies
    }

    fun getDrawableIdName(title : String) : Int {
        return when (title) {
            "openheimerposter" -> R.drawable.openheimerposter
            "harakiri_poster" -> R.drawable.harakiri_poster
            "backtothefuture_poster" -> R.drawable.backtothefuture_poster
            "thepianist_poster" -> R.drawable.thepianist_poster
            else -> R.drawable.cinefylogo
        }
    }
    fun findMovieByTitle(movies: List<Movie>, title: String) : Movie?{
        return movies.find { it.title.equals(title, ignoreCase = true) }
    }
}