package com.example.listamatijevic.retrofit

import com.example.listamatijevic.model.Exercise
import retrofit2.Call
import retrofit2.http.GET

// Define a service interface for your API
interface ApiService {
    @GET("exercises")
    fun getExercises(): Call<List<Exercise>>
}