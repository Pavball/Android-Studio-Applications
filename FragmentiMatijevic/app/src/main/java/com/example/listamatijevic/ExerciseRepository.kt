package com.example.listamatijevic

import androidx.lifecycle.LiveData

class ExerciseRepository(private val exerciseDao: ExerciseDao) {
    val allExercises: LiveData<List<Exercise>> = exerciseDao.getExercise()

    suspend fun insert(exercises: List<Exercise>) {
        exerciseDao.insertExercises(exercises)
    }

    suspend fun delete(exercises: List<Exercise>) {
        exerciseDao.deleteExercises(exercises)
    }

    fun getExerciseById(exerciseId: Long): LiveData<Exercise> {
        return exerciseDao.getExerciseById(exerciseId)
    }

    suspend fun deleteAllExercises() {
        exerciseDao.nukeTable()
    }
}
