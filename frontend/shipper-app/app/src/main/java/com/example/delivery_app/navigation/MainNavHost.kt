package com.example.delivery_app.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.ui.screen.HomeView
import com.example.delivery_app.ui.screen.InformationOrder

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomeView(navController = navController)
        }
        composable("information") {
            InformationOrder(navController = navController)
        }
        composable ("qrcode") {
//            QRScannerScreen(navController = navController)
        }
    }
}