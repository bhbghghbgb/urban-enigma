package com.example.delivery_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.delivery_app.R
import com.example.delivery_app.model.DetailOrders
import com.example.delivery_app.model.Order
import com.example.delivery_app.viewmodel.OrderViewModel
import com.example.delivery_app.viewmodel.State
import com.example.delivery_app.util.FormatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationOrder(navController: NavController, id: String, viewModel: OrderViewModel) {
    val order by viewModel.order.observeAsState()
    val state by viewModel.state.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.getOrderById(id);
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { TopBarCenterOrderInformation(navController) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            when (state) {
                State.LOADING -> {
                    LoadingScreen()
                }

                State.ERROR -> {
                    ErrorScreen {
                        viewModel.getOrderById(id)
                    }
                }

                State.SUCCESS -> {
                    ContentInformationOrder(paddingValues, order = order!!, viewModel = viewModel)
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCenterOrderInformation(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(0.dp),
                clip = false
            )
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.White
            ),
            title = {
                Text(
                    "Information",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }
        )
    }
}

@Composable
fun ContentInformationOrder(paddingValues: PaddingValues, order: Order, viewModel: OrderViewModel) {
    val formatDateTime = FormatDateTime()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.background(Color.White)
        ) {
            item {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "ID: ", color = Color.Gray, fontSize = 16.sp)
                    Text(text = "${order.id}", fontSize = 16.sp)
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .wrapContentHeight()
                            .padding(8.dp)
                            .background(Color.White)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "LocationIcon",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${order.deliveryLocation}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.wrapContentHeight()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "LocationIcon",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${order.user.commonuser.name}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.wrapContentHeight()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "LocationIcon",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${order.user.commonuser.phone}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.wrapContentHeight()
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "LocationIcon",
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${formatDateTime.formattedDateTime(order.orderDateTime)}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.wrapContentHeight()
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                ) {
                    Column {
                        order.detailOrders.forEach { item ->
                            ItemProduct(detailOrders = item)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Subtotal",
                                fontSize = 16.sp,
                                color = Color.Gray,
                            )
                            Text(
                                text = "${viewModel.getTotalOrderNotDiscount(order.id)}$",
                                fontSize = 18.sp,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Delivery Fee",
                                fontSize = 16.sp,
                                color = Color.Gray,
                            )
                            Text(
                                text = "FREE",
                                fontSize = 16.sp,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Discount",
                                fontSize = 16.sp,
                                color = Color.Gray,
                            )
                            Text(
                                text = "${order.discount}$",
                                fontSize = 16.sp,
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 20.sp,
                            )
                            Text(
                                text = "${viewModel.getTotalOrderHaveDiscount(order.id)}$",
                                fontSize = 20.sp,
                                color = Color(0xFFCC6600)
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        if (order.status != "delivered") {
                            Divider(
                                color = Color(0xFF9c7550),
                                thickness = 1.dp,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Button(
                                onClick = { },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF9c7550),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Delivery", fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemProduct(detailOrders: DetailOrders) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(top = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            painter = painterResource(id = R.drawable.mocha),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.width(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "${detailOrders.product.name}",
                    maxLines = 1,
                    fontSize = 17.sp,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${detailOrders.product.description}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Text(
                    text = "x${detailOrders.amount}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    color = Color.Black,
                )
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${detailOrders.product.price * detailOrders.amount}$",
                    fontSize = 19.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF331900)
                )
            }
        }
    }
}