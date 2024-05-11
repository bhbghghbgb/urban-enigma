package com.doansgu.cafectm.ui.main

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.doansgu.cafectm.model.AuthorizationManager
import com.doansgu.cafectm.ui.screen.LogInScreen
import com.doansgu.cafectm.ui.screen.NavigateHomeScreen
import com.doansgu.cafectm.ui.screen.OnboardingScreen
import com.doansgu.cafectm.ui.screen.RegisterScreen
import com.doansgu.cafectm.ui.screen.WelcomeScreen
import com.doansgu.cafectm.viewmodel.AuthViewModel
import com.doansgu.cafectm.viewmodel.NavControllerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold {
        val authorization: String? = AuthorizationManager.authorization
        val viewModel = AuthViewModel();
        val isValidToken by viewModel.isValidToken.observeAsState()
        if (authorization != null) {
            viewModel.authenticate()
        }
        if (isValidToken == false && authorization != null) {
            AuthorizationManager.clearAuthorization()
        }
        val startDestination =
            if (authorization != null && isValidToken == true) "homescreen" else "onboarding"
        if (authorization == null || (isValidToken != null && isValidToken == true)) {
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



