package com.example.appexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appexercise.data.Exercise
import com.example.appexercise.repository.ExerciseRepository
import kotlinx.coroutines.launch

class ExerciseViewModel : ViewModel() {
    private val repository = ExerciseRepository()
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchExercises(type: String? = null, muscle: String? = null, difficulty: String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _exercises.value = repository.getExercises(type, muscle, difficulty)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
                _exercises.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 