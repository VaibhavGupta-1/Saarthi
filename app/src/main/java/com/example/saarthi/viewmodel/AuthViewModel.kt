package com.example.saarthi.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit

// Represents the state of the OTP verification process
sealed class OtpAuthState {
    object Idle : OtpAuthState()
    object Loading : OtpAuthState()
    object Success : OtpAuthState()
    data class Error(val message: String) : OtpAuthState()
    data class CodeSent(val verificationId: String) : OtpAuthState()
}

class AuthViewModel : ViewModel() {
    private val _otpState = MutableStateFlow<OtpAuthState>(OtpAuthState.Idle)
    val otpState = _otpState.asStateFlow()

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storedVerificationId: String? = null

    fun sendOtp(phoneNumber: String, activity: Activity) {
        _otpState.value = OtpAuthState.Loading
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // This is for auto-retrieval, we can handle it later if needed
                    _otpState.value = OtpAuthState.Success
                }

                override fun onVerificationFailed(e: com.google.firebase.FirebaseException) {
                    _otpState.value = OtpAuthState.Error(e.message ?: "An unknown error occurred")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    storedVerificationId = verificationId
                    _otpState.value = OtpAuthState.CodeSent(verificationId)
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otp: String) {
        if (storedVerificationId != null) {
            _otpState.value = OtpAuthState.Loading
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, otp)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _otpState.value = OtpAuthState.Success
                    } else {
                        _otpState.value = OtpAuthState.Error("OTP verification failed. Please try again.")
                    }
                }
        } else {
            _otpState.value = OtpAuthState.Error("Verification ID is missing. Please request a new OTP.")
        }
    }
}