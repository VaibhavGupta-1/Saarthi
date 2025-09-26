package com.example.saarthi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.viewmodel.SharedQuizViewModel

@OptIn(ExperimentalMaterial3Api::class) // Required for Scaffold
@Composable
fun AssessmentResultsScreen(
    navController: NavController,
    viewModel: SharedQuizViewModel
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Your Assessment Results", style = MaterialTheme.typography.headlineMedium)
                Text("Here's a summary of your strengths and interests from the quiz.", style = MaterialTheme.typography.bodyMedium)
            }

            item {
                AptitudeCard()
            }

            item {
                CareerInterestsCard()
            }

            item {
                Button(
                    onClick = {
                        navController.navigate("main") {
                            popUpTo(0) // Pop up to the very first screen in the back stack
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Continue to Dashboard", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}


@Composable
fun AptitudeCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Aptitude Score", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            AptitudeBar("Analytical", 0.9f)
            AptitudeBar("Creative", 0.7f)
            AptitudeBar("Leadership", 0.6f)
            AptitudeBar("Problem Solving", 0.85f)
            AptitudeBar("Communication", 0.75f)
        }
    }
}

@Composable
fun AptitudeBar(label: String, score: Float) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = score,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Composable
fun CareerInterestsCard() {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Career Interests", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            CareerInterestItem("Software Development", 92)
            CareerInterestItem("Data Science", 88)
            CareerInterestItem("Product Management", 82)
            CareerInterestItem("UI/UX Design", 75)
            CareerInterestItem("Cybersecurity", 58)
        }
    }
}

@Composable
fun CareerInterestItem(career: String, percentage: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(career, style = MaterialTheme.typography.bodyLarge)
        Text("$percentage%", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
    }
}