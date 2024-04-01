package com.example.learncode.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.learncode.R
import com.example.learncode.model.NavigationItem
import com.example.learncode.ui.components.ImageSliderWithIndicator
import com.example.learncode.ui.screen.DetailScreen
import com.example.learncode.ui.screen.HomeScreen
import com.example.learncode.ui.screen.LogInScreen
import com.example.learncode.ui.screen.NavigateHomeScreen
import com.example.learncode.ui.screen.OnboardingScreen
import com.example.learncode.ui.screen.OrderScreen
import com.example.learncode.ui.screen.ProfileScreen
import com.example.learncode.ui.screen.RegisterScreen
import com.example.learncode.ui.screen.TransactionScreen
import com.example.learncode.ui.screen.WelcomeScreen
import com.example.learncode.ui.theme.fontPoppinsSemi

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
////
    val navController = rememberNavController()

    Scaffold() {
        NavHost(navController, startDestination = "onboarding") {
            composable(
                "onboarding",
                deepLinks = listOf(navDeepLink {
                    uriPattern = "android-app://androidx.navigation/{onboarding}"
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
                NavigateHomeScreen()
            }
        }
    }
}
//        NavHost(navController, startDestination = "welcome_screen_route") {
//            composable("welcome_screen_route",
//                deepLinks = listOf(navDeepLink {
//                    uriPattern = "android-app://androidx.navigation/{welcome_screen_route}"
//                })
//            ) {
//                WelcomeScreen(navController = navController)
//            }
//            composable("main_screen_route") {
//                DetailScreen(navController = navController)
//            }
//        }



