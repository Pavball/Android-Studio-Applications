package com.example.listamatijevic

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ExerciseDao {

    @Upsert
    suspend fun insertExercises(exercises: List<Exercise>)

    @Delete
    suspend fun deleteExercises(exercises: List<Exercise>)

    @Query("SELECT * FROM exercise_table ORDER BY name DESC")
    fun getExercise(): LiveData<List<Exercise>>

    @Query("DELETE FROM exercise_table")
    fun nukeTable()

    @Query("SELECT * FROM exercise_table WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: Long): LiveData<Exercise>

}
