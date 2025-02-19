package com.example.cinefy.data

import com.example.cinefy.R
import com.example.cinefy.ui.model.Movie

object DataCinefy {
    val movieList: () -> MutableList<Movie> = {
        mutableListOf<Movie>(
            Movie(
                rank = 32,
                title = "OppenHeimer",
                description = "The story of American scientist, J. Robert Oppenheimer, " +
                        "and his role in the development of the atomic bomb.",
                imageUrl = "openheimerposter",
                bigImageUrl = "openheimerposter",
                genres = "History",
                thumbnailUrl = "openheimerposter",
                rating = 8.6f,
                id = "top32",
                year = 2023,
                imdbId = "tt15398776",
                imdbLink = "https://www.imdb.com/title/tt15398776"
            ),
            Movie(
                rank = 33,
                title = "Harakiri",
                description = "When a ronin requesting seppuku at a feudal lord's palace is told of the brutal suicide of another ronin who previously visited",
                imageUrl = "harakiri_poster",
                bigImageUrl = "harakiri_poster",
                genres = "Action",
                thumbnailUrl = "harakiri_poster",
                rating = 8.6f,
                id = "top33",
                year = 1962,
                imdbId = "tt0056058",
                imdbLink = "https://www.imdb.com/title/tt0056058"
            ),
            Movie(
                rank = 34,
                title = "Back to the Future",
                description = "Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the maverick scientist Doc Brown.",
                imageUrl = "backtothefuture_poster",
                bigImageUrl = "backtothefuture_poster",
                genres = "Adventure",
                thumbnailUrl = "backtothefuture_poster",
                rating = 8.5f,
                id = "top34",
                year = 1985,
                imdbId = "tt0088763",
                imdbLink = "https://www.imdb.com/title/tt0088763"
            ),
            Movie(
                rank = 35,
                title = "The Pianist",
                description = "A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto of World War II.",
                imageUrl = "thepianist_poster",
                bigImageUrl = "thepianist_poster",
                genres = "Drama",
                thumbnailUrl = "thepianist_poster",
                rating = 8.5f,
                id = "top35",
                year = 2002,
                imdbId = "tt0253474",
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