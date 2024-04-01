package com.example.learncode.navigation

import androidx.navigation.NavController

class AppNavigator(val navController: NavController) {
    fun navigationToDestination(destinationId: Int) {
        navController.navigate(destinationId)
    }
}