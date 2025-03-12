package com.example.cinefy.localdatabase

import androidx.room.TypeConverter
import com.example.cinefy.datamodel.Comment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromListToString(genres: List<String>): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun fromStringToList(genresString: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(genresString, type)
    }

    // 🔹 Métodos para convertir List<Comment> a String y viceversa
    @TypeConverter
    fun fromCommentList(comments: List<Comment>): String {
        return gson.toJson(comments)
    }

    @TypeConverter
    fun toCommentList(commentsString: String): List<Comment> {
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(commentsString, type)
    }
}
