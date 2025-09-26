package com.example.saarthi.data.model

data class User(
    val id: String, // Firebase Auth UID
    val fullName: String,
    val highestEducation: String,
    val keyInterests: List<String>
)