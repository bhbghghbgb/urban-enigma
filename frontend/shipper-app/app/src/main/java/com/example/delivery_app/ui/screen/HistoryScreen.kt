package com.example.delivery_app.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.delivery_app.model.Order
import com.example.delivery_app.util.FormatDateTime
import com.example.delivery_app.viewmodel.HomeViewModel
import com.example.delivery_app.viewmodel.State

@Composable
fun HistoryScreen(navController: NavController, viewModel: HomeViewModel) {
    val orders by viewModel.orders.observeAsState()
    val state by viewModel.state.observeAsState()
    when (state) {
        State.LOADING -> {
            LoadingScreen()
        }

        State.SUCCESS -> {
            LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxWidth()) {
                orders?.let { orders ->
                    orders.forEach { order ->
                        item {
                            OrderItem(
                                navController = navController, order = order, viewModel = viewModel
                            )
                        }
                        item { Spacer(modifier = Modifier.height(10.dp)) }
                    }
                }
            }
        }

        State.ERROR -> {
            ErrorScreen {
                viewModel.getOrdersByStaff()
            }
        }

        else -> {}
    }
}

@Composable
fun OrderItem(navController: NavController, order: Order, viewModel: HomeViewModel) {
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
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 7.dp)
                ) {
                    Text(
                        text = "Order ID: ${order.id}",
                        maxLines = 2,
                        fontSize = 15.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Delivered",
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
                        text = "${formatDateTime.formattedDateTime(order.orderDateTime)}",
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