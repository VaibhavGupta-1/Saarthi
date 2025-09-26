package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saarthi.data.model.Career
import com.example.saarthi.data.model.QuizAnswers
import com.example.saarthi.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _recommendations = MutableStateFlow<List<Career>>(emptyList())
    val recommendations: StateFlow<List<Career>> = _recommendations

    // A state for loading and errors
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchRecommendations(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // Create the dummy data that the backend needs
                val dummyAnswers = QuizAnswers(q1 = "Analytical")

                // Pass the dummy data in the API call
                val result = RetrofitInstance.api.getRecommendations(userId, dummyAnswers)

                _recommendations.value = result
            } catch (e: Exception) {
                _error.value = "Failed to fetch recommendations: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}