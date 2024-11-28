package com.example.cinefy.model

data class Movie(
    val  rank : Int,
    val title : String,
    val descripcion : String,
    val image : String,
    val bigimage : String,
    val genre : String,
    val thumbanil : String,
    val ranting : Float,
    val id : String,
    val yearEstreno : Int,
    val imdbid : String,
    val imdbid_link : String
)