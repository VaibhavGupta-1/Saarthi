package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.saarthi.data.remote.RetrofitInstance
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

// Represents the different outcomes of a login attempt
sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object NewUser : LoginState() // User is new, go to details screen
    object ExistingUser : LoginState() // User exists, go to dashboard
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            Firebase.auth.signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        checkIfUserExists(authResult.result.user?.uid)
                    } else {
                        _loginState.value = LoginState.Error("Firebase authentication failed.")
                    }
                }
        }
    }

    private fun checkIfUserExists(userId: String?) {
        if (userId == null) {
            _loginState.value = LoginState.Error("Could not get user ID.")
            return
        }

        viewModelScope.launch {
            try {
                // Try to get the user's profile from our backend
                RetrofitInstance.api.getUser(userId)
                // If it succeeds, the user exists
                _loginState.value = LoginState.ExistingUser
            } catch (e: Exception) {
                if (e is HttpException && e.code() == 404) {
                    // 404 Not Found means this is a new user
                    _loginState.value = LoginState.NewUser
                } else {
                    // Any other error
                    _loginState.value = LoginState.Error("Error checking user: ${e.message}")
                }
            }
        }
    }
}