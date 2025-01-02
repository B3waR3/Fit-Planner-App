package com.example.appexercise

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
            
            if (navHostFragment == null) {
                Log.e("MainActivity", "NavHostFragment not found")
                Toast.makeText(this, "Navigation initialization failed", Toast.LENGTH_LONG).show()
                return
            }

            if (navHostFragment.navController == null) {
                Log.e("MainActivity", "NavController not found")
                Toast.makeText(this, "Navigation initialization failed", Toast.LENGTH_LONG).show()
                return
            }
            navController = navHostFragment.navController
            
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}