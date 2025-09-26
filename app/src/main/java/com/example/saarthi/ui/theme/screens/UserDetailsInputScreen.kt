package com.example.saarthi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.viewmodel.UserProfileViewModel

@Composable
fun UserDetailsInputScreen(
    navController: NavController,
    viewModel: UserProfileViewModel
) {
    var fullName by remember { mutableStateOf("") }
    var highestEducation by remember { mutableStateOf("") }
    var keyInterests by remember { mutableStateOf("") }
    val isButtonEnabled = fullName.isNotBlank() && highestEducation.isNotBlank() && keyInterests.isNotBlank()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Tell us about yourself", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Help us personalize your career journey.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(32.dp))

            val textFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )

            OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = highestEducation, onValueChange = { highestEducation = it }, label = { Text("Highest Education") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = keyInterests, onValueChange = { keyInterests = it }, label = { Text("Key Interests (e.g., Technology, Arts)") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Save data locally to the shared ViewModel
                    viewModel.updateUserDetails(fullName, highestEducation, keyInterests)
                    navController.navigate("quiz")
                },
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Continue", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}