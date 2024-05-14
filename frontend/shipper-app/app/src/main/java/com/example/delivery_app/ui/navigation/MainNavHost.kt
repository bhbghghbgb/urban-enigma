package com.example.delivery_app.ui.navigation

import com.example.delivery_app.ui.screen.LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.model.AuthorizationManager
import com.example.delivery_app.viewmodel.AuthViewModel
import com.example.delivery_app.viewmodel.HomeViewModel
import com.example.delivery_app.viewmodel.OrderViewModel
import com.example.delivery_app.viewmodel.ProfileViewModel
import com.example.delivery_app.ui.screen.HomeView
import com.example.delivery_app.ui.screen.InformationOrder

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val token: String? = AuthorizationManager.authorization
    val viewModel = AuthViewModel();
    val homeViewModel = remember {
        HomeViewModel()
    }
    val profileViewModel = remember {
        ProfileViewModel()
    }

    val orderViewModel = remember {
        OrderViewModel()
    }
//    val isValidToken by viewModel.isValidToken.observeAsState()
//    if (token != null) {
//        viewModel.authenticate(token)
//    }
//    if (isValidToken == false && token != null) {
//        PreferenceManager.clearToken(LocalContext.current)
//    }
//    val startDestination = if (token != null && isValidToken == true) "home" else "login"
    val startDestination = "login"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController = navController, viewModel = AuthViewModel())
        }
        composable("home") {
            HomeView(
                navControllerMain = navController,
                viewModel = homeViewModel,
                profileViewModel = profileViewModel
            )
        }
        composable("information/{id}") {
            InformationOrder(
                navController = navController,
                it.arguments?.getString("id").toString(),
                viewModel = orderViewModel
            )
        }
        composable("qrcode") {
//            QRScannerScreen(navController = navController)
        }
    }
}