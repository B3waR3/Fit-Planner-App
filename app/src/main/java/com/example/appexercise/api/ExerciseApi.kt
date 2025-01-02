package com.example.appexercise.api

import com.example.appexercise.data.Exercise
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExerciseApi {
    @Headers(
        "X-Api-Key: k9KqLFxbq7/ddeC9j6yFkA==dc2PzcuiGNST9NoH"
    )
    @GET("v1/exercises")
    suspend fun getExercises(
        @Query("type") type: String? = null,
        @Query("muscle") muscle: String? = null,
        @Query("difficulty") difficulty: String? = null
    ): List<Exercise>
} 