package com.example.appexercise.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appexercise.R
import com.example.appexercise.data.Exercise
import com.google.android.material.card.MaterialCardView

class ExerciseAdapter(
    private var exercises: List<Exercise> = emptyList(),
    private val onItemClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: MaterialCardView = view.findViewById(R.id.cardView)
        val nameText: TextView = view.findViewById(R.id.exerciseName)
        val typeText: TextView = view.findViewById(R.id.exerciseType)
        val muscleText: TextView = view.findViewById(R.id.exerciseMuscle)
        val difficultyText: TextView = view.findViewById(R.id.exerciseDifficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercise, parent, false)
        return ExerciseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.nameText.text = exercise.name
        holder.typeText.text = "Type: ${exercise.type}"
        holder.muscleText.text = "Muscle: ${exercise.muscle}"
        holder.difficultyText.text = "Difficulty: ${exercise.difficulty}"
        
        holder.cardView.setOnClickListener {
            onItemClick(exercise)
        }
    }

    override fun getItemCount() = exercises.size

    fun updateExercises(newExercises: List<Exercise>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
} 