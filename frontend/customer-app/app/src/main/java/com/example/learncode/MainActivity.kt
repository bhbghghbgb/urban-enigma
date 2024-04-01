package com.example.learncode

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.learncode.model.NavigationItem
import com.example.learncode.ui.main.MainScreen
import com.example.learncode.ui.screen.DetailScreen
import com.example.learncode.ui.screen.HomeScreen
import com.example.learncode.ui.theme.LearnCodeTheme
import com.example.learncode.ui.screen.LogInScreen
import com.example.learncode.ui.screen.NavigateHomeScreen
import com.example.learncode.ui.screen.OnboardingScreen
import com.example.learncode.ui.screen.OrderScreen
import com.example.learncode.ui.screen.ProfileScreen
import com.example.learncode.ui.screen.RegisterScreen
import com.example.learncode.ui.screen.TransactionScreen
import com.example.learncode.ui.screen.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "welcomescreen", showBackground = true)
@Composable
fun GreetingPreview() {
    LearnCodeTheme {
        MainScreen()
    }
}