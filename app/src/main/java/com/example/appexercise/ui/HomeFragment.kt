package com.example.appexercise.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appexercise.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAllExercises).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_allExercisesFragment)
        }

        view.findViewById<Button>(R.id.btnMyWorkouts).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_myWorkoutsFragment)
        }
    }
} 