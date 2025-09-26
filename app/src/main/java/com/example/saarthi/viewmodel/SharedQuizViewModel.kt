package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedQuizViewModel : ViewModel() {
    // A private mutable state flow to hold the answers
    private val _answers = MutableStateFlow<Map<Int, String>>(emptyMap())

    // A public immutable state flow for UI to observe
    val answers = _answers.asStateFlow()

    // Function to update the answers from the Quiz Screen
    fun submitAnswers(newAnswers: Map<Int, String>) {
        _answers.value = newAnswers
    }
}