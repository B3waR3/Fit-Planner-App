package com.example.appexercise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appexercise.R
import com.example.appexercise.data.Exercise
import com.example.appexercise.data.SavedExercise
import com.example.appexercise.viewmodel.SavedWorkoutsViewModel

class ExerciseDetailFragment : Fragment() {
    private lateinit var savedWorkoutsViewModel: SavedWorkoutsViewModel
    private var exercise: Exercise? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercise = arguments?.getParcelable("exercise")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add back press handler
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        savedWorkoutsViewModel = ViewModelProvider(requireActivity())[SavedWorkoutsViewModel::class.java]

        exercise?.let { exercise ->
            view.findViewById<TextView>(R.id.exerciseName).text = exercise.name
            view.findViewById<TextView>(R.id.exerciseType).text = "Type: ${exercise.type}"
            view.findViewById<TextView>(R.id.exerciseMuscle).text = "Muscle: ${exercise.muscle}"
            view.findViewById<TextView>(R.id.exerciseDifficulty).text = "Difficulty: ${exercise.difficulty}"
            view.findViewById<TextView>(R.id.exerciseEquipment).text = "Equipment: ${exercise.equipment}"
            view.findViewById<TextView>(R.id.exerciseInstructions).text = exercise.instructions

            val setsPicker = view.findViewById<NumberPicker>(R.id.setsPicker).apply {
                minValue = 1
                maxValue = 10
                value = 3
            }

            val repsPicker = view.findViewById<NumberPicker>(R.id.repsPicker).apply {
                minValue = 1
                maxValue = 30
                value = 12
            }

            view.findViewById<Button>(R.id.addToWorkoutsButton).setOnClickListener {
                val savedExercise = SavedExercise(
                    exercise = exercise,
                    sets = setsPicker.value,
                    reps = repsPicker.value
                )
                savedWorkoutsViewModel.addExercise(savedExercise)
                Toast.makeText(requireContext(), "Added to workouts", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_exerciseDetailFragment_to_myWorkoutsFragment)
            }
        }
    }

    companion object {
        fun newInstance(exercise: Exercise) = ExerciseDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("exercise", exercise)
            }
        }
    }
} 