package com.example.appexercise.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appexercise.R
import com.example.appexercise.data.SavedExercise
import com.google.android.material.card.MaterialCardView

class SavedWorkoutsAdapter(
    private val onDeleteClick: (SavedExercise) -> Unit
) : RecyclerView.Adapter<SavedWorkoutsAdapter.ViewHolder>() {

    private var exercises = listOf<SavedExercise>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.cardView)
        val nameText: TextView = view.findViewById(R.id.exerciseName)
        val typeText: TextView = view.findViewById(R.id.exerciseType)
        val muscleText: TextView = view.findViewById(R.id.exerciseMuscle)
        val difficultyText: TextView = view.findViewById(R.id.exerciseDifficulty)
        val setsText: TextView = view.findViewById(R.id.exerciseSets)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_exercise, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val savedExercise = exercises[position]
        val exercise = savedExercise.exercise
        
        holder.nameText.text = exercise.name
        holder.typeText.text = "Type: ${exercise.type}"
        holder.muscleText.text = "Muscle: ${exercise.muscle}"
        holder.difficultyText.text = "Difficulty: ${exercise.difficulty}"
        holder.setsText.text = "Sets: ${savedExercise.sets}"
        
        holder.deleteButton.setOnClickListener {
            onDeleteClick(savedExercise)
        }
    }

    override fun getItemCount() = exercises.size

    fun updateExercises(newExercises: List<SavedExercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
} 