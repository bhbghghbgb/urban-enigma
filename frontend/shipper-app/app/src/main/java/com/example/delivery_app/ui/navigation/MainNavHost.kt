package com.example.delivery_app.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.ui.screen.HomeView
import com.example.delivery_app.ui.screen.InformationOrder
import com.example.delivery_app.ui.screen.LoginScreen
import com.example.delivery_app.ui.screen.QRTestScreen
import com.example.delivery_app.util.activityViewModel
import com.example.delivery_app.viewmodel.AuthViewModel
import com.example.delivery_app.viewmodel.HomeViewModel
import com.example.delivery_app.viewmodel.OrderViewModel
import com.example.delivery_app.viewmodel.ProfileViewModel
import com.example.delivery_app.viewmodel.QRTestViewModel

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val startDestination = "login"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            val authViewModel: AuthViewModel = activityViewModel()
            LoginScreen(navController = navController, authViewModel)
        }
        composable("home") {
            val homeViewModel: HomeViewModel = viewModel()
            val profileViewModel: ProfileViewModel = viewModel()
            val qrCodeViewModel: QRTestViewModel = activityViewModel()
            HomeView(
                navControllerMain = navController,
                homeViewModel = homeViewModel,
                profileViewModel = profileViewModel,
                qrCodeViewModel = qrCodeViewModel,
            )
        }
        composable("information/{id}") {
            val orderViewModel: OrderViewModel = viewModel()
            InformationOrder(
                navController = navController,
                it.arguments?.getString("id").toString(),
                viewModel = orderViewModel
            )
        }
    }
}