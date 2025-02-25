package com.example.cinefy.ui.localdatabase

import androidx.room.TypeConverter
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
}
