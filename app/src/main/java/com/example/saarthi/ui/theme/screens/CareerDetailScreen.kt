package com.example.saarthi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.data.mock.MockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareerDetailScreen(navController: NavController, careerId: Int) {
    val career = MockData.getMockCareers().find { it.id == careerId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(career?.name ?: "Career Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (career == null) {
            // Show an error message if the career is not found
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Text("Career not found.", modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Text(career.name, style = MaterialTheme.typography.headlineMedium)
                }
                item {
                    Text(career.description, style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Average Salary", style = MaterialTheme.typography.titleLarge)
                    Text(career.averageSalary, style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Required Skills", style = MaterialTheme.typography.titleLarge)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(career.requiredSkills) { skill ->
                            SuggestionChip(onClick = {}, label = { Text(skill) })
                        }
                    }
                }
            }
        }
    }
}