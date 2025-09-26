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
fun CollegeDetailScreen(navController: NavController, collegeId: Int) {
    val college = MockData.getMockColleges().find { it.id == collegeId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(college?.name ?: "College Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (college == null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Text("College not found.", modifier = Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    Text(college.name, style = MaterialTheme.typography.headlineMedium)
                    Text(college.location, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }
                item {
                    Text(college.description, style = MaterialTheme.typography.bodyLarge)
                }
                item {
                    Text("Popular Courses", style = MaterialTheme.typography.titleLarge)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(college.popularCourses) { course ->
                            SuggestionChip(onClick = {}, label = { Text(course) })
                        }
                    }
                }
                item {
                    Text("Facilities", style = MaterialTheme.typography.titleLarge)
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(college.facilities) { facility ->
                            SuggestionChip(onClick = {}, label = { Text(facility) })
                        }
                    }
                }
            }
        }
    }
}