package com.example.cinefy.ui.localdatabase

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.cinefy.ui.model.Comment

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
