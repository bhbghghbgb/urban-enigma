package com.doansgu.cafectm.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.doansgu.cafectm.model.NavigationItem
import com.doansgu.cafectm.viewmodel.CartViewModel
import com.doansgu.cafectm.viewmodel.HomeScreen2ViewModel
import com.doansgu.cafectm.viewmodel.NavControllerViewModel
import com.doansgu.cafectm.viewmodel.ProfileViewModel
import com.doansgu.cafectm.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NavigateHomeScreen(navControllerViewModel: NavControllerViewModel) {
    val navController = rememberNavController()
    val showBottomBar = remember { mutableStateOf(true) }
    val profileViewModel = remember { ProfileViewModel() }
    val cartViewModel = remember { CartViewModel() }
    val homeScreen2ViewModel = remember { HomeScreen2ViewModel() }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        showBottomBar.value = when (destination.route) {
            "menuproduct", "detail/{_id}", "information/{_id}", "login" -> false
            else -> true
        }
    }
    Scaffold(bottomBar = {
        if (showBottomBar.value) {
            // Your BottomBar goes here
            BottomBar(navController = navController)
        }
    }) {
        BottomNavGraph(
            navController = navController,
            { BottomBar(navController = navController) },
            profileViewModel,
            cartViewModel,
            homeScreen2ViewModel,
            navControllerViewModel
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavigationItem.Home,
        NavigationItem.Order,
        NavigationItem.Transactions,
        NavigationItem.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        modifier = Modifier.height(65.dp),
        contentColor = Color(0xFFEDC0A9),
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    bottom: @Composable () -> Unit,
    profileViewModel: ProfileViewModel,
    cartViewModel: CartViewModel,
    homeScreen2ViewModel: HomeScreen2ViewModel,
    navControllerViewModel: NavControllerViewModel
) {

    val viewModel = remember {
        SearchViewModel()
    }
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        composable(route = NavigationItem.Home.route) {
            HomeScreen2(navController = navController, homeScreen2ViewModel, bottom)
        }
        composable(route = NavigationItem.Order.route) {
            OrderScreen(navController = navController, bottom, cartViewModel)
        }
        composable(route = NavigationItem.Transactions.route) {
            TransactionScreen(navController = navController)
        }
        composable(route = NavigationItem.Profile.route) {
            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel,
                navControllerViewModel = navControllerViewModel
            )
        }
        composable("menuproduct") {
            val focusRequester = remember {
                FocusRequester()
            }
            MenuProducts(navController, focusRequester, viewModel)
        }
        composable("detail/{_id}") {
            DetailScreen(navController, it.arguments?.getString("_id").toString())
        }
        composable("information/{_id}") {
            InformationOrder(
                navController = navController, it.arguments?.getString("_id").toString()
            )
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: NavigationItem, currentDestination: NavDestination?, navController: NavHostController
) {
    NavigationBarItem(label = {
        Text(text = screen.title, fontSize = 10.sp)
    }, icon = {
        Icon(imageVector = screen.icon, contentDescription = null)
    }, selected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true, onClick = {
        navController.navigate(screen.route) {
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    })
}