package com.doansgu.cafectm.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.doansgu.cafectm.ui.screen.LoginScreen
import com.doansgu.cafectm.ui.screen.NavigateHomeScreen
import com.doansgu.cafectm.ui.screen.OnboardingScreen
import com.doansgu.cafectm.ui.screen.RegisterScreen
import com.doansgu.cafectm.ui.screen.WelcomeScreen
import com.doansgu.cafectm.util.activityViewModel
import com.doansgu.cafectm.viewmodel.AuthViewModel
import com.doansgu.cafectm.viewmodel.NavControllerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold {
        val startDestination = "login"
        NavHost(navController, startDestination = startDestination) {
            composable(
                "onboarding", deepLinks = listOf(navDeepLink {
                    uriPattern = "android-app://androidx.navigation/onboarding"
                })
            ) {
                OnboardingScreen(navController = navController)
            }
            composable("welcome") {
                WelcomeScreen(navController = navController)
            }
            composable("login") {
                val authViewModel: AuthViewModel = activityViewModel()
                LoginScreen(navController = navController, authViewModel)
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
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}



