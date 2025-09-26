package com.example.saarthi.data.remote

import com.example.saarthi.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("api/users/")
    suspend fun createUser(@Body user: User): User

    @GET("api/users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): User

    @POST("api/users/{user_id}/recommendations/")
    suspend fun getRecommendations(
        @Path("user_id") userId: String,
        @Body answers: QuizAnswers // <-- ADD THIS PARAMETER
    ): List<Career>

    @GET("api/colleges/")
    suspend fun getColleges(): List<College>

    @GET("api/skills/")
    suspend fun getSkills(): List<Skill>
}