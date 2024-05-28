package com.example.listamatijevic

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class ExerciseViewModel(private val repository: ExerciseRepository) : ViewModel() {
    val allExercises: LiveData<List<Exercise>> = repository.allExercises

    fun addExercises(exercises: List<Exercise>) {
        viewModelScope.launch {
            repository.insert(exercises)
        }
    }

    // Function to delete all exercises
    fun deleteAllExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllExercises()
        }
    }

    fun getExerciseById(exerciseId: Long): LiveData<Exercise> {
        return repository.getExerciseById(exerciseId)
    }
}

class ExerciseViewModelFactory(private val repository: ExerciseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExerciseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
