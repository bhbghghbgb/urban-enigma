package com.doansgu.cafectm.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.doansgu.cafectm.R
import com.doansgu.cafectm.model.AuthorizationManager
import com.doansgu.cafectm.model.NavigationItem
import com.doansgu.cafectm.model.Order
import com.doansgu.cafectm.model.Products
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.viewmodel.OrderViewModel
import com.doansgu.cafectm.viewmodel.State
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(navController: NavController) {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val viewModel = remember { OrderViewModel() }
    val tabItems = listOf(
        TabItem("Current"),
        TabItem("History"),
    )
    val pagerState = rememberPagerState(pageCount = {
        tabItems.size
    })
    val scope = rememberCoroutineScope()
    val token: String? = AuthorizationManager.authorization
    LaunchedEffect(key1 = true) {
        if (token != null) {
            viewModel.getOrdersNotYetDelivered()
            viewModel.getOrdersDelivered()
        }
    }
//    InformationOrder()
    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = pagerState.currentPage, indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                height = 4.dp,
                color = Color(0xFF9C7055)
            )
        }) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        selectedTabIndex = index
                        scope.launch { pagerState.scrollToPage(index) }
                    },
                    text = {
                        Text(
                            text = item.title, fontSize = 20.sp, fontFamily = FontFamily(
                                fontPoppinsRegular
                            )
                        )
                    },
                    selectedContentColor = Color(0xFF9C7055),
                    unselectedContentColor = Color.LightGray
                )
            }
        }
        HorizontalPager(
            beyondBoundsPageCount = tabItems.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { currentPage ->
            when (currentPage) {
                0 -> {
                    if (token != null) {
                        HistoryScreen(navController, viewModel = viewModel, token = token)
                    }
                }

                1 -> {
                    if (token != null) {
                        DeliveredScreen(
                            navController = navController,
                            viewModel = viewModel,
                            token = token
                        )
                    }
                }
            }
        }
    }
}


data class TabItem(
    val title: String,
    val icon: ImageVector? = null,
)

@Composable
fun HistoryScreen(navController: NavController, viewModel: OrderViewModel, token: String) {
    val orders by viewModel.orders.observeAsState()
    val state by viewModel.state.observeAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxSize()) {
        when (state) {
            State.LOADING -> {
                item { LoadingScreen() }
            }

            State.SUCCESS -> {
                orders?.let { order ->
                    for (it in order) {
                        item {
                            OrderItem(
                                navController = navController,
                                order = it,
                                viewModel,
                                false
                            )
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }
                }
            }

            State.ERROR -> {
                item {
                    ErrorScreen(
                        errorMessage = "Failed to load orders.",
                        onRetry = {
                            token?.let {
                                viewModel.getOrdersNotYetDelivered()
                            }
                        }
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun DeliveredScreen(navController: NavController, viewModel: OrderViewModel, token: String) {
    val orders by viewModel.orderDelivered.observeAsState()
    val state by viewModel.state.observeAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.fillMaxSize()) {
        when (state) {
            State.LOADING -> {
                item { LoadingScreen() }
            }

            State.SUCCESS -> {
                orders?.let { order ->
                    for (it in order) {
                        item {
                            OrderItem(
                                navController = navController,
                                order = it,
                                viewModel,
                                true
                            )
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }
                }
            }

            State.ERROR -> {
                item {
                    ErrorScreen(
                        errorMessage = "Failed to load orders.",
                        onRetry = {
                            token?.let {
                                viewModel.getOrdersNotYetDelivered()
                            }
                        }
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun ErrorScreen(errorMessage: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = onRetry) {
                Text(text = "Retry", color = Color.White)
            }
        }
    }
}

@Composable
fun OrderItem(
    navController: NavController,
    order: Order,
    viewModel: OrderViewModel,
    status: Boolean
) {
    val timestamp = order.orderDateTime
    val date = Date(timestamp.time)
    val formattedDateOfBirth = SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date)
    var amount = 0
    var total = 0.0
    for (item in order.detailOrders) {
        amount += item.amount
        total += item.product.price * item.amount
    }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                navController.navigate("information/${order.id}")
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
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_shipper),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = viewModel.getListNameProducts(order.id, status),
                        maxLines = 2,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = order.status.capitalize(),
                        maxLines = 1,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.wrapContentWidth(),
                        color = when (order.status) {
                            "delivered" -> Color(0xFF009900)
                            "delivering" -> Color(0xFFCCCC00)
                            else -> Color(0xFFFF0000)
                        }
                    )
                    Text(
                        text = "$total$",
                        maxLines = 1,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
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
                        text = "$amount Items",
                        maxLines = 1,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(fontPoppinsSemi),
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                    Text(
                        text = formattedDateOfBirth,
                        maxLines = 1,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(fontPoppinsSemi),
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationOrder(navController: NavController, id: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { TopBarCenterOrderInformation(navController) },
            containerColor = Color.White
        ) { paddingValues ->
            ContentInformationOrder(paddingValues, id)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCenterOrderInformation(navController: NavController) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White
        ),
        title = {
            Text(
                "My Order",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontFamily = FontFamily(fontPoppinsSemi)
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(NavigationItem.Transactions.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }

            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Composable
fun ContentInformationOrder(paddingValues: PaddingValues, id: String) {
    val token = AuthorizationManager.authorization;
    val viewModel = remember {
        OrderViewModel()
    }
    val state = viewModel.state.observeAsState()
    val orderList by viewModel.order.observeAsState()
    LaunchedEffect(Unit) {
        if (token != null) {
            viewModel.getOrderById(id)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.background(Color.Transparent)
        ) {
            when (state.value) {
                State.LOADING -> {
                    item { LoadingScreen() }
                }

                State.SUCCESS -> {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "ID: ", color = Color.Gray, fontSize = 16.sp)
                            Text(text = id, fontSize = 16.sp)
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
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.LocationOn,
                                            contentDescription = "LocationIcon",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = orderList!!.deliveryLocation,
                                            fontSize = 16.sp,
                                            modifier = Modifier.wrapContentHeight()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "LocationIcon",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = orderList!!.user.commonuser.name,
                                            fontSize = 16.sp,
                                            modifier = Modifier.wrapContentHeight()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.Call,
                                            contentDescription = "LocationIcon",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = orderList!!.user.commonuser.phone,
                                            fontSize = 16.sp,
                                            modifier = Modifier.wrapContentHeight()
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Row {
                                        Icon(
                                            imageVector = Icons.Default.MailOutline,
                                            contentDescription = "NoteIcon",
                                            tint = Color.Gray
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = orderList!!.note,
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
                                orderList?.let { orderList ->
                                    orderList.detailOrders.forEach { detailOrder ->
                                        val product = Products(
                                            _id = detailOrder.product._id,
                                            image = "",
                                            name = detailOrder.product.name,
                                            description = detailOrder.product.description,
                                            price = detailOrder.product.price,
                                            popular = detailOrder.product.popular,
                                            category = detailOrder.product.category,
                                            avgRating = 0.0
                                        )
                                        ItemProduct(product = product, detailOrder.amount)
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Subtotal",
                                        fontSize = 16.sp,
                                        color = Color.Gray,
                                        fontFamily = FontFamily(fontPoppinsRegular)
                                    )
                                    Text(
                                        text = viewModel.getTotalOrderNotDiscount(orderId = orderList!!.id)
                                            .toString() + "$",
                                        fontSize = 18.sp,
                                        fontFamily = FontFamily(fontPoppinsSemi)
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
                                        fontFamily = FontFamily(fontPoppinsRegular)
                                    )
                                    Text(
                                        text = "FREE",
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(fontPoppinsSemi)
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
                                        fontFamily = FontFamily(fontPoppinsRegular)
                                    )
                                    Text(
                                        text = "${orderList!!.discount}$",
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(fontPoppinsSemi)
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
                                        fontFamily = FontFamily(fontPoppinsSemi)
                                    )
                                    Text(
                                        text = viewModel.getTotalOrderHaveDiscount(orderId = orderList!!.id)
                                            .toString() + "$",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(fontPoppinsSemi),
                                        color = Color(0xFFCC6600)
                                    )
                                }
                            }
                        }
                    }
                }

                State.ERROR -> {
                    item {
                        ErrorScreen(
                            errorMessage = "Failed to load orders.",
                            onRetry = {
                                token?.let {
                                    viewModel.getOrdersNotYetDelivered()
                                }
                            }
                        )
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable

fun ItemProduct(product: Products, amount: Int) {
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
                    text = product.name,
                    maxLines = 1,
                    fontSize = 17.sp,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = product.description,
                    fontFamily = FontFamily(fontPoppinsRegular),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Text(
                    text = "x$amount",
                    fontFamily = FontFamily(fontPoppinsRegular),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    color = Color.Black,
                )
            }
            Column(
                modifier = Modifier.wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product.price.toString() + "$",
                    fontFamily = FontFamily(fontPoppinsSemi),
                    fontSize = 19.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF331900)
                )
            }
        }
    }
}