package com.example.appexercise.repository

import com.example.appexercise.api.ExerciseApi
import com.example.appexercise.data.Exercise
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ExerciseRepository {
    private val api: ExerciseApi

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ExerciseApi::class.java)
    }

    suspend fun getExercises(
        type: String? = null,
        muscle: String? = null,
        difficulty: String? = null
    ): List<Exercise> {
        return api.getExercises(type, muscle, difficulty)
    }
} 