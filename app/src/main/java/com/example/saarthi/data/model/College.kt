package com.example.saarthi.data.model

data class College(
    val id: Int,
    val name: String,
    val location: String,
    val description: String,            // <-- ADD THIS
    val popularCourses: List<String>,   // <-- ADD THIS
    val facilities: List<String>        // <-- ADD THIS
)