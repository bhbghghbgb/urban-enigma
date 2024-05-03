package com.example.delivery_app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.R
import com.example.delivery_app.data.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(navController: NavController) {
    val profileViewModel = remember { ProfileViewModel() }
    var currentScreen by rememberSaveable {
        mutableStateOf("home")
    }
    var title by rememberSaveable {
        mutableStateOf("How do you feel today?")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, fontSize = 18.sp)
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF9c7055),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.background
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = if (currentScreen == "home") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    label = {
                        Text(
                            "Home",
                            fontSize = 12.sp,
                            color = if (currentScreen == "home") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    selected = currentScreen == "home",
                    onClick = {
                        currentScreen = "home"
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.ic_baseline_history_24),
                            contentDescription = "History",
                            tint = if (currentScreen == "history") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    label = {
                        Text(
                            "History",
                            fontSize = 12.sp,
                            color = if (currentScreen == "history") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    selected = currentScreen == "history",
                    onClick = {
                        currentScreen = "history"
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    label = {
                        Text(
                            "Profile",
                            fontSize = 12.sp,
                            color = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                        )
                    },
                    selected = currentScreen == "profile",

                    onClick = {
                        currentScreen = "profile"
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentScreen) {
                "home" -> {
                    title = "Current orders"
                    HomeScreen(navController = navController)
                }

                "history" -> {
                    title = "Delivered order history"
                    HistoryScreen(navController = navController)
                }

                "profile" -> {
                    title = "Your profile"
                    ProfileScreen(profileViewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxWidth()) {
        item { OrderItemNow(navController = navController) }
    };
}

@Composable
fun OrderItemNow(navController: NavController) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate("information")
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Order ID: 6610d75d520537d636970c49",
                    maxLines = 2,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "To: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = "273 An Duong Vuong, P5, Q5",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Customer: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = "Nguyễn Thùy Linh",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Number phone: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = "0987654321",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Divider(
                    color = Color(0xFF9c7055),
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Now",
                        maxLines = 1,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                        color = Color(0xFF009900)
                    )
                    Text(
                        text = "+2.80$",
                        maxLines = 1,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                        color = Color(0xFF006633)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "6 Items",
                        maxLines = 1,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                    Text(
                        text = "12/03/2024 07:07",
                        maxLines = 1,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}