package com.example.saarthi.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserProfileViewModel : ViewModel() {
    private val _fullName = MutableStateFlow("Priya") // Default value
    val fullName = _fullName.asStateFlow()

    private val _education = MutableStateFlow("")
    val education = _education.asStateFlow()

    private val _interests = MutableStateFlow("")
    val interests = _interests.asStateFlow()

    fun updateUserDetails(name: String, edu: String, userInterests: String) {
        _fullName.value = name
        _education.value = edu
        _interests.value = userInterests
    }
}