package com.example.saarthi.ui.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.saarthi.R
import com.example.saarthi.viewmodel.AuthViewModel
import com.example.saarthi.viewmodel.OtpAuthState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var mobileNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as Activity // Needed for OTP verification
    val otpState by authViewModel.otpState.collectAsState()

    LaunchedEffect(key1 = otpState) {
        authViewModel.otpState.collectLatest { state ->
            when (state) {
                is OtpAuthState.CodeSent -> navController.navigate("otp/$mobileNumber")
                is OtpAuthState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                else -> { /* Do nothing for Idle, Loading, or Success states here */ }
            }
        }
    }

    // --- Google Sign-In Launcher ---
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                Firebase.auth.signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            // On successful login, go to the details screen to create a profile
                            navController.navigate("details") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Firebase Authentication Failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Sign-in failed with code: ${e.statusCode}")
                Toast.makeText(context, "Google Sign-In Failed. Error Code: ${e.statusCode}", Toast.LENGTH_LONG).show()
            }
        }
    )

    // --- Google Sign-In Configuration ---
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("780384182699-6iv3cpgtdt8abk6ffb512jcetelcvgsl.apps.googleusercontent.com") // <-- IMPORTANT: Replace with your actual Web Client ID from Firebase
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    // --- UI ---
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.saarthi_logo),
                    contentDescription = "Saarthi Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Saarthi", style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(64.dp))

                OutlinedTextField(
                    value = mobileNumber,
                    onValueChange = { mobileNumber = it },
                    label = { Text("Enter Mobile Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (mobileNumber.isNotBlank() && mobileNumber.length == 10) {
                            authViewModel.sendOtp(mobileNumber, activity)
                        } else {
                            Toast.makeText(context, "Please enter a valid 10-digit number", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    enabled = otpState !is OtpAuthState.Loading
                ) {
                    Text("Get OTP", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("or")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { googleSignInLauncher.launch(googleSignInClient.signInIntent) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = MaterialTheme.shapes.medium,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Text("Sign-in with Google", style = MaterialTheme.typography.titleMedium)
                }
            }

            // Show a loading spinner if OTP is being sent
            if (otpState is OtpAuthState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}