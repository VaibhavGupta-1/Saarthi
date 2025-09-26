package com.example.saarthi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.saarthi.viewmodel.AuthViewModel
import com.example.saarthi.viewmodel.OtpAuthState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtpScreen(
    navController: NavController,
    mobileNumber: String,
    authViewModel: AuthViewModel
) {
    var otp by remember { mutableStateOf("") }
    val otpState by authViewModel.otpState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = otpState) {
        authViewModel.otpState.collectLatest { state ->
            when (state) {
                is OtpAuthState.Success -> {
                    // On successful verification, go to the details screen to create a profile
                    navController.navigate("details") {
                        popUpTo("login") { inclusive = true }
                    }
                }
                is OtpAuthState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                else -> { /* Do nothing for Idle or CodeSent states here */ }
            }
        }
    }

    // --- UI ---
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Enter OTP",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "An OTP has been sent to +91 $mobileNumber",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = otp,
                    onValueChange = { if (it.length <= 6) otp = it },
                    label = { Text("6-Digit OTP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { authViewModel.verifyOtp(otp) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = otp.length == 6 && otpState !is OtpAuthState.Loading
                ) {
                    Text("Verify OTP", style = MaterialTheme.typography.titleMedium)
                }
            }

            // Show a loading spinner if OTP is being verified
            if (otpState is OtpAuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}