package com.example.listamatijevic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Exercise(
    val name: String,
    val description: String,
    val technique: String,
    val imageUrl: String,
    val websiteUrl: String
)
