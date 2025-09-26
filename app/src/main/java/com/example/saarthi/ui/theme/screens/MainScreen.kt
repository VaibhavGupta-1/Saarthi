package com.example.saarthi.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.saarthi.navigation.Screen
import com.example.saarthi.viewmodel.SharedQuizViewModel
import com.example.saarthi.viewmodel.UserProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainNavController: NavController,
    sharedQuizViewModel: SharedQuizViewModel,
    userProfileViewModel: UserProfileViewModel
) {
    val nestedNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val items = listOf(
                    Screen.Dashboard,
                    Screen.Colleges,
                    Screen.Profile
                )

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            nestedNavController.navigate(screen.route) {
                                popUpTo(nestedNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            indicatorColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = "college_detail/{collegeId}",
                arguments = listOf(navArgument("collegeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val collegeId = backStackEntry.arguments?.getInt("collegeId") ?: -1
                CollegeDetailScreen(navController = nestedNavController, collegeId = collegeId)
            }
            composable(Screen.Dashboard.route) {
                PersonalizedDashboardScreen(
                    navController = nestedNavController,
                    quizViewModel = sharedQuizViewModel,
                    profileViewModel = userProfileViewModel
                )
            }
            composable(Screen.Colleges.route) { CollegeDirectoryScreen(nestedNavController) }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navController = nestedNavController,
                    viewModel = userProfileViewModel,
                    onLogout = {
                        mainNavController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }

            // --- THE ROUTE IS NOW CORRECTLY PLACED HERE ---
            composable(
                route = "career_detail/{careerId}",
                arguments = listOf(navArgument("careerId") { type = NavType.IntType })
            ) { backStackEntry ->
                val careerId = backStackEntry.arguments?.getInt("careerId") ?: -1
                CareerDetailScreen(navController = nestedNavController, careerId = careerId)
            }

            composable("chat") { ChatScreen(navController = nestedNavController) }
        }
    }
}