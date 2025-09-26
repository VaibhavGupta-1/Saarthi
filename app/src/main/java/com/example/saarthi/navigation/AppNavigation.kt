package com.example.saarthi.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saarthi.ui.screens.*
import com.example.saarthi.viewmodel.SharedQuizViewModel
import com.example.saarthi.viewmodel.UserProfileViewModel
import com.example.saarthi.viewmodel.AuthViewModel // <-- Import AuthViewModel


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val sharedQuizViewModel: SharedQuizViewModel = viewModel()
    val userProfileViewModel: UserProfileViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "onboarding") {
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("otp/{mobileNumber}") { backStackEntry ->
            val mobileNumber = backStackEntry.arguments?.getString("mobileNumber") ?: ""
            OtpScreen(
                navController = navController,
                mobileNumber = mobileNumber,
                authViewModel = authViewModel
            )
        }
        composable("details") { UserDetailsInputScreen(navController, userProfileViewModel) }
        composable("quiz") { AssessmentQuizScreen(navController, sharedQuizViewModel) }
        composable("results") { AssessmentResultsScreen(navController, sharedQuizViewModel) }

        // The career_detail route has been REMOVED from here.

        composable("main") {
            MainScreen(
                mainNavController = navController,
                sharedQuizViewModel = sharedQuizViewModel,
                userProfileViewModel = userProfileViewModel
            )
        }
    }
}