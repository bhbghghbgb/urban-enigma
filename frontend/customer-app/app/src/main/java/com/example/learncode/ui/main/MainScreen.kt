package com.example.learncode.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.learncode.model.AuthorizationManager
import com.example.learncode.ui.screen.LogInScreen
import com.example.learncode.ui.screen.NavigateHomeScreen
import com.example.learncode.ui.screen.OnboardingScreen
import com.example.learncode.ui.screen.RegisterScreen
import com.example.learncode.ui.screen.WelcomeScreen
import com.example.learncode.viewmodel.AuthViewModel
import com.example.learncode.viewmodel.NavControllerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold {
        val token: String? = AuthorizationManager.getToken(LocalContext.current)
        val viewModel = AuthViewModel();
        val isValidToken by viewModel.isValidToken.observeAsState()
        if (token != null) {
            viewModel.authenticate(token)
        }
        if (isValidToken == false && token != null) {
            AuthorizationManager.clearAuthorization(LocalContext.current)
        }
        val startDestination =
            if (token != null && isValidToken == true) "homescreen" else "onboarding"
        if (token == null || (isValidToken != null && isValidToken == true)) {
            NavHost(navController, startDestination = startDestination) {
                composable(
                    "onboarding",
                    deepLinks = listOf(navDeepLink {
                        uriPattern = "android-app://androidx.navigation/onboarding"
                    })
                ) {
                    OnboardingScreen(navController = navController)
                }
                composable("welcome") {
                    WelcomeScreen(navController = navController)
                }
                composable("login") {
                    LogInScreen(navController = navController)
                }
                composable("register") {
                    RegisterScreen(navController = navController)
                }
                composable("homescreen") {
                    val navControllerViewModel = NavControllerViewModel()
                    navControllerViewModel.setNavController(navController)
                    NavigateHomeScreen(navControllerViewModel)
                }
            }
        } else if (isValidToken == null) {
            LoadingScreen()
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}



