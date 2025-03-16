package com.example.cinefy.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class SingIn (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("userName")
    val userName: String,
    @ColumnInfo("password")
    val password: String,
)
