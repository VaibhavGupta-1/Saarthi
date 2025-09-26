package com.example.saarthi.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.saarthi.data.mock.MockData
import com.example.saarthi.data.model.College
import androidx.compose.foundation.clickable // <-- Make sure this is imported


@Composable
fun CollegeDirectoryScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val colleges = MockData.getMockColleges()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("College Directory", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Colleges...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(colleges.filter { it.name.contains(searchQuery, ignoreCase = true) }) { college ->
                CollegeCard(
                    college = college,
                    onClick = {
                        navController.navigate("college_detail/${college.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun CollegeCard(college: College, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // <-- Add clickable modifier
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(college.name, style = MaterialTheme.typography.titleMedium)
            Text(college.location, style = MaterialTheme.typography.bodySmall)
        }
    }
}