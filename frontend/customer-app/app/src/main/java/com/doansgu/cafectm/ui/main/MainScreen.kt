package com.doansgu.cafectm.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.doansgu.cafectm.model.AuthorizationManager
import com.doansgu.cafectm.ui.screen.LoginScreen
import com.doansgu.cafectm.ui.screen.NavigateHomeScreen
import com.doansgu.cafectm.ui.screen.OnboardingScreen
import com.doansgu.cafectm.ui.screen.RegisterScreen
import com.doansgu.cafectm.ui.screen.WelcomeScreen
import com.doansgu.cafectm.viewmodel.AuthViewModel
import com.doansgu.cafectm.viewmodel.NavControllerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(authViewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold {
        val authorization: String? = AuthorizationManager.authorization
//        val viewModel = AuthViewModel();
//        val isValidToken by viewModel.isValidToken.observeAsState()
//        if (authorization != null) {
//            viewModel.testAuthorization()
//        }
//        if (isValidToken == false && authorization != null) {
//            AuthorizationManager.clearAuthorization()
//        }
//        val startDestination =
//            if (authorization != null && isValidToken == true) "homescreen" else "onboarding"
//        if (authorization == null || (isValidToken != null && isValidToken == true)) {
        val startDestination = "homescreen"
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
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}



