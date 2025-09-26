package com.example.saarthi.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat // Icon import is now included
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.data.mock.MockData
import com.example.saarthi.data.model.Career
import com.example.saarthi.data.model.Deadline
import com.example.saarthi.viewmodel.SharedQuizViewModel
import com.example.saarthi.viewmodel.UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizedDashboardScreen(
    navController: NavController,
    quizViewModel: SharedQuizViewModel,
    profileViewModel: UserProfileViewModel
) {
    val quizAnswers by quizViewModel.answers.collectAsState()
    val fullName by profileViewModel.fullName.collectAsState()
    val firstName = if (fullName.isNotBlank()) fullName.substringBefore(" ") else "User"

    val mainRecommendation = when (quizAnswers.get(0)) {
        "Science and Technology" -> MockData.getMockCareers().find { it.name == "AI/ML Engineer" }
        "Arts and Humanities" -> MockData.getMockCareers().find { it.name == "UI/UX Designer" }
        "Business and Economics" -> MockData.getMockCareers().find { it.name == "Product Manager" }
        "Health and Social Care" -> MockData.getMockCareers().find { it.name == "Data Scientist" }
        else -> MockData.getMockCareers().first()
    } ?: MockData.getMockCareers().first()

    val otherRecommendations = MockData.getMockCareers().filter { it.name != mainRecommendation.name }
    val deadlines = MockData.getMockDeadlines()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personalized Dashboard") },
                actions = {
                    IconButton(onClick = { navController.navigate("chat") }) {
                        Icon(Icons.Default.Chat, contentDescription = "AI Advisor")
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Text("Hello, $firstName!", style = MaterialTheme.typography.headlineMedium)
                Text("Ready to explore your future today?", style = MaterialTheme.typography.bodyMedium)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                MainRecommendationCard(
                    career = mainRecommendation,
                    onClick = { navController.navigate("career_detail/${mainRecommendation.id}") }
                )
            }

            item {
                Text("Other Recommended Paths", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(otherRecommendations) { career ->
                        OtherRecommendationCard(
                            career = career,
                            onClick = { navController.navigate("career_detail/${career.id}") }
                        )
                    }
                }
            }

            item {
                Text("Upcoming Deadlines", style = MaterialTheme.typography.titleLarge)
            }

            items(deadlines) { deadline ->
                DeadlineCard(deadline = deadline)
            }
        }
    }
}
@Composable
fun MainRecommendationCard(career: Career, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(career.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(career.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onClick() }) { // Button also triggers the click
                Text("Match 92%")
            }
        }
    }
}

@Composable
fun OtherRecommendationCard(career: Career, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(career.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(career.description, style = MaterialTheme.typography.bodySmall, maxLines = 3)
        }
    }
}

@Composable
fun DeadlineCard(deadline: Deadline) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(deadline.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    deadline.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            Text(
                text = "Upcoming",
                style = MaterialTheme.typography.bodySmall,
                color = if (deadline.type == "University") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
}