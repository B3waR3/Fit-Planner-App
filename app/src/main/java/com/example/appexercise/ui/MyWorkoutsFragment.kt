package com.example.appexercise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appexercise.R
import com.example.appexercise.viewmodel.SavedWorkoutsViewModel

class MyWorkoutsFragment : Fragment() {
    private lateinit var viewModel: SavedWorkoutsViewModel
    private lateinit var adapter: SavedWorkoutsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_workouts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupViewModel()
        setupRecyclerView(view)
        observeViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[SavedWorkoutsViewModel::class.java]
    }

    private fun setupRecyclerView(view: View) {
        adapter = SavedWorkoutsAdapter(
            onDeleteClick = { savedExercise ->
                viewModel.removeExercise(savedExercise)
            }
        )

        view.findViewById<RecyclerView>(R.id.workoutsRecyclerView).apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = this@MyWorkoutsFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewModel.savedExercises.observe(viewLifecycleOwner) { exercises ->
            val emptyStateText = view?.findViewById<TextView>(R.id.emptyStateText)
            val recyclerView = view?.findViewById<RecyclerView>(R.id.workoutsRecyclerView)

            if (exercises.isEmpty()) {
                emptyStateText?.visibility = View.VISIBLE
                recyclerView?.visibility = View.GONE
            } else {
                emptyStateText?.visibility = View.GONE
                recyclerView?.visibility = View.VISIBLE
                adapter.updateExercises(exercises)
            }
        }
    }
} 