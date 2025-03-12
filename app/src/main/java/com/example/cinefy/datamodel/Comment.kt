package com.example.cinefy.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("userName")
    val author: String,
    @ColumnInfo("commentText")
    val content: String
)