package com.example.saarthi.data.model

data class Career(
    val id: Int,
    val name: String,
    val description: String,
    val requiredSkills: List<String>,
    val averageSalary: String
)