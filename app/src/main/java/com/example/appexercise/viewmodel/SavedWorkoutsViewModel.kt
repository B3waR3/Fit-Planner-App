package com.example.appexercise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appexercise.data.SavedExercise

class SavedWorkoutsViewModel : ViewModel() {
    private val _savedExercises = MutableLiveData<List<SavedExercise>>(emptyList())
    val savedExercises: LiveData<List<SavedExercise>> = _savedExercises

    fun addExercise(savedExercise: SavedExercise) {
        val currentList = _savedExercises.value.orEmpty().toMutableList()
        currentList.add(savedExercise)
        _savedExercises.value = currentList
    }

    fun removeExercise(savedExercise: SavedExercise) {
        val currentList = _savedExercises.value.orEmpty().toMutableList()
        currentList.remove(savedExercise)
        _savedExercises.value = currentList
    }
} 