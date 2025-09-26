package com.example.saarthi.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.saarthi.R
import com.example.saarthi.viewmodel.UserProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: UserProfileViewModel,
    onLogout: () -> Unit
) {
    val name by viewModel.fullName.collectAsState()
    val education by viewModel.education.collectAsState()
    val interests by viewModel.interests.collectAsState()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // --- USE LazyColumn INSTEAD OF Column FOR SCROLLING ---
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text("Profile", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                // --- PROFILE PICTURE SECTION WITH IMPROVED EDIT BUTTON ---
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { imagePickerLauncher.launch("image/*") }
                    ) {
                        if (imageUri != null) {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Add Profile Picture",
                                modifier = Modifier.size(70.dp).align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    // --- THE NEW, IMPROVED EDIT BUTTON (FAB) ---
                    SmallFloatingActionButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.align(Alignment.BottomEnd),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Photo",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // --- TEXT FIELDS SECTION ---
            item {
                val textFieldColors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                )
                OutlinedTextField(value = name, onValueChange = { viewModel.updateUserDetails(it, education, interests) }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = education, onValueChange = { viewModel.updateUserDetails(name, it, interests) }, label = { Text("Education") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = interests, onValueChange = { viewModel.updateUserDetails(name, education, it) }, label = { Text("Interests") }, modifier = Modifier.fillMaxWidth(), colors = textFieldColors)
                Spacer(modifier = Modifier.height(32.dp))
            }

            // --- BUTTONS SECTION ---
            item {
                Button(onClick = { /* Handle save logic */ }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = MaterialTheme.shapes.medium) { Text("Save Changes", style = MaterialTheme.typography.titleMedium) }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = onLogout, modifier = Modifier.fillMaxWidth().height(50.dp), shape = MaterialTheme.shapes.medium, border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)) { Text("Log Out", style = MaterialTheme.typography.titleMedium) }
            }

            // --- FEEDBACK SECTION ---
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Feedback", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Scan the QR code to share your feedback about the app or a college.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.feedback_qr_code),
                    contentDescription = "Feedback QR Code",
                    modifier = Modifier.size(150.dp)
                )
            }
        }
    }
}