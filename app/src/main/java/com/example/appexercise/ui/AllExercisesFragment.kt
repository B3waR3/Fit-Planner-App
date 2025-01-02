package com.example.appexercise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.appexercise.R
import com.example.appexercise.data.Exercise
import com.example.appexercise.data.SavedExercise
import com.example.appexercise.viewmodel.ExerciseViewModel
import com.example.appexercise.viewmodel.SavedWorkoutsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AllExercisesFragment : Fragment() {
    private lateinit var viewModel: ExerciseViewModel
    private lateinit var savedWorkoutsViewModel: SavedWorkoutsViewModel
    private lateinit var adapter: ExerciseAdapter

    // Add adapter properties
    private lateinit var typeAdapter: ArrayAdapter<String>
    private lateinit var muscleAdapter: ArrayAdapter<String>
    private lateinit var difficultyAdapter: ArrayAdapter<String>

    private val exerciseTypes = listOf("All", "cardio", "olympic_weightlifting", "plyometrics", "powerlifting", "strength", "stretching", "strongman")
    private val muscleGroups = listOf("All", "abdominals", "abductors", "adductors", "biceps", "calves", "chest", "forearms", "glutes", "hamstrings", "lats", "lower_back", "middle_back", "neck", "quadriceps", "traps", "triceps")
    private val difficultyLevels = listOf("All", "beginner", "intermediate", "expert")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize adapters here
        typeAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, exerciseTypes)
        muscleAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, muscleGroups)
        difficultyAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, difficultyLevels)

        return inflater.inflate(R.layout.fragment_all_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView(view)
        setupFilters(view)
        observeViewModel()

        // Initial load of exercises
        viewModel.fetchExercises()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        savedWorkoutsViewModel = ViewModelProvider(requireActivity())[SavedWorkoutsViewModel::class.java]
    }

    private fun setupRecyclerView(view: View) {
        adapter = ExerciseAdapter(onItemClick = { exercise ->
            val bundle = Bundle().apply {
                putParcelable("exercise", exercise)
            }
            findNavController().navigate(
                R.id.action_allExercisesFragment_to_exerciseDetailFragment,
                bundle
            )
        })

        view.findViewById<RecyclerView>(R.id.exercisesRecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@AllExercisesFragment.adapter
        }
    }

    private fun setupFilters(view: View) {
        // Setup type filter
        view.findViewById<AutoCompleteTextView>(R.id.typeFilterAutoComplete).apply {
            setAdapter(typeAdapter)
            setText(exerciseTypes[0], false)
            setOnClickListener {
                showDropDown()
            }
            setOnItemClickListener { _, _, position, _ ->
                setText(exerciseTypes[position], false)
                applyFilters(view)
            }
        }

        // Setup muscle filter
        view.findViewById<AutoCompleteTextView>(R.id.muscleFilterAutoComplete).apply {
            setAdapter(muscleAdapter)
            setText(muscleGroups[0], false)
            setOnClickListener {
                showDropDown()
            }
            setOnItemClickListener { _, _, position, _ ->
                setText(muscleGroups[position], false)
                applyFilters(view)
            }
        }

        // Setup difficulty filter
        view.findViewById<AutoCompleteTextView>(R.id.difficultyFilterAutoComplete).apply {
            setAdapter(difficultyAdapter)
            setText(difficultyLevels[0], false)
            setOnClickListener {
                showDropDown()
            }
            setOnItemClickListener { _, _, position, _ ->
                setText(difficultyLevels[position], false)
                applyFilters(view)
            }
        }
    }

    private fun applyFilters(view: View) {
        val type = view.findViewById<AutoCompleteTextView>(R.id.typeFilterAutoComplete)
            .text.toString()
            .takeIf { it != "All" }
        val muscle = view.findViewById<AutoCompleteTextView>(R.id.muscleFilterAutoComplete)
            .text.toString()
            .takeIf { it != "All" }
        val difficulty = view.findViewById<AutoCompleteTextView>(R.id.difficultyFilterAutoComplete)
            .text.toString()
            .takeIf { it != "All" }

        viewModel.fetchExercises(type, muscle, difficulty)
    }

    private fun observeViewModel() {
        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            adapter.updateExercises(exercises)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            view?.findViewById<View>(R.id.progressBar)?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }
} 