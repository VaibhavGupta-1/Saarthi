package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saarthi.data.model.User
import com.example.saarthi.data.remote.RetrofitInstance
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailsViewModel : ViewModel() {
    private val _saveStatus = MutableStateFlow<SaveStatus>(SaveStatus.Idle)
    val saveStatus: StateFlow<SaveStatus> = _saveStatus

    fun saveUserDetails(fullName: String, highestEducation: String, keyInterests: String) {
        val userId = Firebase.auth.currentUser?.uid
        if (userId == null) {
            _saveStatus.value = SaveStatus.Error("User is not logged in.")
            return
        }

        viewModelScope.launch {
            _saveStatus.value = SaveStatus.Loading
            try {
                val user = User(
                    id = userId,
                    fullName = fullName,
                    highestEducation = highestEducation,
                    // Splitting the comma-separated string into a list
                    keyInterests = keyInterests.split(",").map { it.trim() }
                )
                // Call the API to create the user profile
                RetrofitInstance.api.createUser(user)
                _saveStatus.value = SaveStatus.Success
            } catch (e: Exception) {
                _saveStatus.value = SaveStatus.Error("Failed to save details: ${e.message}")
            }
        }
    }
}

// A sealed class to represent the state of the save operation
sealed class SaveStatus {
    object Idle : SaveStatus()
    object Loading : SaveStatus()
    object Success : SaveStatus()
    data class Error(val message: String) : SaveStatus()
}