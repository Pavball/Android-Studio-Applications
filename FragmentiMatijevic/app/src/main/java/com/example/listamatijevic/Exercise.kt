package com.example.listamatijevic

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val technique: String,
    val imageResourceId: Int,
    val websiteUrl: String
)
