package com.example.learncode.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Home : NavigationItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    object Order : NavigationItem(
        route = "order",
        title = "My Order",
        icon = Icons.Default.ShoppingCart
    )

    object Transactions : NavigationItem(
        route = "transactions",
        title = "Transactions",
        icon = Icons.Default.DateRange
    )

    object Profile : NavigationItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )
}
