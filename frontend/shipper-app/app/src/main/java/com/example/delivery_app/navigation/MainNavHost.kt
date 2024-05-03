package com.example.delivery_app.navigation

import LoginScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.data.viewmodel.AuthViewModel
import com.example.delivery_app.ui.screen.HomeView
import com.example.delivery_app.ui.screen.InformationOrder
import com.example.learncode.model.PreferenceManager

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val token: String? = PreferenceManager.getToken(LocalContext.current)
    val viewModel = AuthViewModel();
    val isValidToken by viewModel.isValidToken.observeAsState()
    if(token!=null) {
        viewModel.authenticate(token)
    }
    if(isValidToken == false && token !=null) {
        PreferenceManager.clearToken(LocalContext.current)
    }
    val startDestination = if (token != null && isValidToken == true) "home" else "login"
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController = navController, viewModel = AuthViewModel())
        }
        composable("home") {
            HomeView(navController = navController)
        }
        composable("information") {
            InformationOrder(navController = navController)
        }
        composable("qrcode") {
//            QRScannerScreen(navController = navController)
        }
    }
}