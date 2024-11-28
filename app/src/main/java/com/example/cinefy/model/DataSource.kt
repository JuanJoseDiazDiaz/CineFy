package com.example.cinefy.model

object DataSource {
    val movieList: () -> MutableList<Movie> = {
        mutableListOf<Movie>(
            Movie(
                rank = 32,
                title = "OppenHeimer",
                descripcion = "The story of American scientist, J. Robert Oppenheimer, " +
                        "and his role in the development of the atomic bomb.",
                image = "openheimerposter",
                bigimage = "openheimerposter",
                genre = "History",
                thumbanil = "openheimerposter",
                ranting = 8.6f,
                id = "top32",
                yearEstreno = 2023,
                imdbid = "tt15398776",
                imdbid_link = "https://www.imdb.com/title/tt15398776"
            ),
            Movie(
                rank = 33,
                title = "Harakiri",
                descripcion = "When a ronin requesting seppuku at a feudal lord's palace is told of the brutal suicide of another ronin who previously visited, he reveals how their pasts are intertwined - and in doing so challenges the clan's integrity.",
                image = "harakiri_poster",
                bigimage = "harakiri_poster",
                genre = "Action",
                thumbanil = "harakiri_poster",
                ranting = 8.6f,
                id = "top33",
                yearEstreno = 1962,
                imdbid = "tt0056058",
                imdbid_link = "https://www.imdb.com/title/tt0056058"
            ),
            Movie(
                rank = 34,
                title = "Back to the Future",
                descripcion = "Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the maverick scientist Doc Brown.",
                image = "backtothefuture_poster",
                bigimage = "backtothefuture_poster",
                genre = "Adventure",
                thumbanil = "backtothefuture_poster",
                ranting = 8.5f,
                id = "top34",
                yearEstreno = 1985,
                imdbid = "tt0088763",
                imdbid_link = "https://www.imdb.com/title/tt0088763"
            ),
            Movie(
                rank = 35,
                title = "The Pianist",
                descripcion = "A Polish Jewish musician struggles to survive the destruction of the Warsaw ghetto of World War II.",
                image = "thepianist_poster",
                bigimage = "thepianist_poster",
                genre = "Drama",
                thumbanil = "thepianist_poster",
                ranting = 8.5f,
                id = "top35",
                yearEstreno = 2002,
                imdbid = "tt0253474",
                imdbid_link = "https://www.imdb.com/title/tt0253474"
            )
        ).apply { shuffle()}
    }
}