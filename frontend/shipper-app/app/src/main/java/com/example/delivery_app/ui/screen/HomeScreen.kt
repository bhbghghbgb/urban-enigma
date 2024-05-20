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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.compose.rememberNavController
import com.example.delivery_app.R
import com.example.delivery_app.model.Order
import com.example.delivery_app.util.FormatDateTime
import com.example.delivery_app.viewmodel.HomeViewModel
import com.example.delivery_app.viewmodel.ProfileViewModel
import com.example.delivery_app.viewmodel.QRTestViewModel
import com.example.delivery_app.viewmodel.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navControllerMain: NavController,
    homeViewModel: HomeViewModel,
    profileViewModel: ProfileViewModel,
    qrCodeViewModel: QRTestViewModel,
) {
    val navController = rememberNavController()
    var currentScreen by rememberSaveable {
        mutableStateOf("home")
    }
    var title by rememberSaveable {
        mutableStateOf("How do you feel today?")
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = title, fontSize = 18.sp)
            }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFF9c7055), titleContentColor = Color.White
            )
        )
    }, bottomBar = {
        NavigationBar {
            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = if (currentScreen == "home") Color(0xFF9c7055) else Color.Black
                )
            }, label = {
                Text(
                    "Home",
                    fontSize = 12.sp,
                    color = if (currentScreen == "home") Color(0xFF9c7055) else Color.Black
                )
            }, selected = currentScreen == "home", onClick = {
                currentScreen = "home"
            })
            NavigationBarItem(icon = {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_history_24),
                    contentDescription = "History",
                    tint = if (currentScreen == "history") Color(0xFF9c7055) else Color.Black
                )
            }, label = {
                Text(
                    "History",
                    fontSize = 12.sp,
                    color = if (currentScreen == "history") Color(0xFF9c7055) else Color.Black
                )
            }, selected = currentScreen == "history", onClick = {
                currentScreen = "history"
            })
            NavigationBarItem(icon = {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    tint = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                )
            }, label = {
                Text(
                    "Profile",
                    fontSize = 12.sp,
                    color = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                )
            }, selected = currentScreen == "profile", onClick = {
                currentScreen = "profile"
            })
            NavigationBarItem(icon = {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_qrcode_24),
                    contentDescription = "Scanner",
                    tint = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                )
            }, label = {
                Text(
                    "Scanner",
                    fontSize = 12.sp,
                    color = if (currentScreen == "profile") Color(0xFF9c7055) else Color.Black
                )
            }, selected = currentScreen == "scanner", onClick = {
                currentScreen = "scanner"
            })
        }
    }) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentScreen) {
                "home" -> {
                    title = "Current orders"
                    HomeScreen(navControllerMain, navController = navController, viewModel = homeViewModel)
                }

                "history" -> {
                    title = "Delivered order history"
                    HistoryScreen(navController = navController, viewModel = homeViewModel)
                }

                "profile" -> {
                    title = "Your profile"
                    ProfileScreen(
                        navControllerMain = navControllerMain, viewModel = profileViewModel
                    )
                }

                "scanner" -> {
                    title = "Scanner"
                    QRTestScreen(qrCodeViewModel)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(mainNavController: NavController, navController: NavController, viewModel: HomeViewModel) {
    val order by viewModel.order.observeAsState()
    val state by viewModel.state.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getOrderByStaff()
    }
    when (state) {
        State.LOADING -> {
            LoadingScreen()
        }

        State.SUCCESS -> {
            LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxWidth()) {
                item {
                    if (order != null) {
                        OrderItemNow(
                            navController = mainNavController, order = order!!, viewModel = viewModel
                        )
                    } else {
                        Text(
                            text = "There are currently no orders",
                            maxLines = 1,
                            fontSize = 16.sp,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Gray
                        )
                    }
                }
            };
        }

        State.ERROR -> {
            ErrorScreen {
                viewModel.getOrderByStaff()
            }
        }

        else -> {}
    }
}

@Composable
fun OrderItemNow(navController: NavController, order: Order, viewModel: HomeViewModel) {
    val formatDateTime = FormatDateTime()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate("information/${order.id}")
            }, shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
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
                    text = "Order ID: ${order.id}",
                    maxLines = 2,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "To: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = order.deliveryLocation,
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Customer: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = order.user.commonuser.name,
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "Number phone: ",
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = order.user.commonuser.phone,
                        maxLines = 1,
                        fontSize = 17.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color(0xFF9c7055)
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
                        text = "+${viewModel.getTotalOrderHaveDiscount(order.id)}$",
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
                        text = "${viewModel.getTotalItem(order.id)} Items",
                        maxLines = 1,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                    Text(
                        text = formatDateTime.formattedDateTime(order.orderDateTime),
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