package com.doansgu.cafectm.ui.screen

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.doansgu.cafectm.R
import com.doansgu.cafectm.model.AddToCartRequest
import com.doansgu.cafectm.model.Cart
import com.doansgu.cafectm.model.PaymentState
import com.doansgu.cafectm.model.ProductOfCart
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.util.backendImageRoute
import com.doansgu.cafectm.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController, bottom: @Composable () -> Unit, viewModel: CartViewModel
) {
    Box {
        Scaffold(
            topBar = { TopBarCenter(navController) },
            containerColor = Color.Transparent,
            bottomBar = bottom
        ) { paddingValues ->
            ContentOrder(paddingValues = paddingValues, viewModel, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCenter(navController: NavController) {
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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentOrder(
    paddingValues: PaddingValues, viewModel: CartViewModel, navController: NavController
) {
    val cart by viewModel.cart.observeAsState()
    var isEditAddressDialogVisible by remember { mutableStateOf(false) }
    val user by viewModel.userData.observeAsState()
    var noteText by remember { mutableStateOf("") }
    val deliveryAddress by viewModel.address.observeAsState("")
    var selectedPaymentMethod by remember { mutableStateOf("Cash") }
    val total by viewModel.total.collectAsState()
    val activity = LocalContext.current as? Activity
    var pointUsed by remember { mutableStateOf(0) }
    LaunchedEffect(cart, total, user) {
        viewModel.fetchData()
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(contentPadding = paddingValues, modifier = Modifier.fillMaxSize()) {
            item {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Delivery Address",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = deliveryAddress.toString(),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        color = Color.Gray
                    )
                    Row {
                        Button(
                            onClick = { isEditAddressDialogVisible = true },
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "EditIcon",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Edit Address",
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(fontPoppinsRegular)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(
                            onClick = { /* location services deleted*/ },
                            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "EditIcon",
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Get Location",
                                fontSize = 14.sp,
                                color = Color.Black,
                                fontFamily = FontFamily(fontPoppinsRegular)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    ElevatedCard(
                        modifier = Modifier.wrapContentSize(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 5.dp
                        )
                    ) {
                        TextField(
                            value = noteText,
                            onValueChange = { noteText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            maxLines = 5,
                            placeholder = {
                                Text(
                                    "Write a note here...",
                                    color = Color.LightGray,
                                    fontFamily = FontFamily(
                                        fontPoppinsRegular
                                    ),
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    if (cart != null) {
                        for (item in cart!!.products) {
                            var amount by remember { mutableStateOf(item.amount) }
                            ItemOrder(product = item,amount, onDeleteClicked = {
                                val addToCart = item.product.id?.let { AddToCartRequest(it) }
                                if (addToCart != null) {
                                    viewModel.deleteProduct(
                                        addToCart
                                    )
                                }
                            }, onIncreaseClicked = {
//                                item.product.id?.let { viewModel.increaseProductQuantity(it) }
                                amount++
                                viewModel.increase(detailOfCart = item, price = item.price, amount = amount)
                            }, onDecreaseClicked = {
//                                val addToCart = item.product.id?.let { AddToCartRequest(it) }
//                                item.product.id?.let {
//                                    if (addToCart != null) {
//                                        viewModel.decreaseProductQuantity(
//                                            it, addToCart
//                                        )
//                                    }
//                                }
                                if (amount > 1) {
                                    amount--
                                    viewModel.decrease(detailOfCart = item, price = item.price, amount = amount)
                                }
                            }, onItemClick = {
                                navController.navigate("detail/${item.product.id}")
                            })
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(5.dp)) }
            item {
                if (cart == null) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(35.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Payment Summary",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(fontPoppinsSemi)
                        )
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
                                text = "${total}$",
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
                                text = "${user!!.membershipPoint}",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(fontPoppinsSemi)
                            )
                            // Declaring a boolean value for storing checked state
                            val mCheckedState = remember{ mutableStateOf(false)}

                            // Creating a Switch, when value changes,
                            // it updates mCheckedState value
                            Switch(checked = mCheckedState.value, onCheckedChange = {mCheckedState.value = it})
                            if (mCheckedState.value) {
                                pointUsed = user!!.membershipPoint
                                Text(text = "-${pointUsed * 10} VND",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(fontPoppinsSemi))
                            }
                        }

                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Payment Method",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    PaymentMethodItem(text = "Cash",
                        isSelected = selectedPaymentMethod == "Cash",
                        onClick = { selectedPaymentMethod = "Cash" })
                    PaymentMethodItem(text = "Momo",
                        isSelected = selectedPaymentMethod == "Momo",
                        onClick = { selectedPaymentMethod = "Momo" })
                    PaymentMethodItem(text = "Banking",
                        isSelected = selectedPaymentMethod == "Banking",
                        onClick = { selectedPaymentMethod = "Banking" })
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                cart?.let { cart ->
                    if (activity != null) {
                        BottomCheckOut(cart = cart, viewModel, activity, pointUsed, selectedPaymentMethod, deliveryAddress, noteText)
                    }
                }
            }
        }
    }
    if (isEditAddressDialogVisible) {
        EditAddressDialog(onDismiss = { isEditAddressDialogVisible = false },
            onSave = { newAddress ->
                viewModel.setAddress(newAddress)
                isEditAddressDialogVisible = false
            })
    }
}


@Composable
fun PaymentMethodItem(
    text: String, isSelected: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.Gray,
            fontFamily = FontFamily(fontPoppinsRegular)
        )
        RadioButton(
            selected = isSelected, onClick = onClick
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAddressDialog(
    onDismiss: () -> Unit, onSave: (String) -> Unit
) {
    var address by remember { mutableStateOf("") }
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.Transparent,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = {
                        Text(
                            text = "Edit Address",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(fontPoppinsSemi)
                        )
                    },
                    text = {
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            TextField(
                                value = address,
                                onValueChange = { address = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .background(Color.Transparent),
                                maxLines = 5,
                                textStyle = TextStyle(fontSize = 16.sp),
                                label = {
                                    Text(
                                        "Enter your address",
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(fontPoppinsRegular)
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    containerColor = Color.LightGray.copy(alpha = 0.1f),
                                    focusedIndicatorColor = Color(0xFFFFFF),
                                    unfocusedIndicatorColor = Color(0xFFFFFF)
                                )
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                onSave(address)
                                onDismiss()
                            },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(end = 8.dp)
                                .height(48.dp)
                                .clip(RoundedCornerShape(24.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9c7055)),
                        ) {
                            Text(
                                "Save",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(fontPoppinsSemi),
                                color = Color.White
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(start = 8.dp)
                                .height(48.dp)
                                .clip(RoundedCornerShape(24.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray.copy(
                                    alpha = 0.1f
                                )
                            ),
                        ) {
                            Text(
                                "Cancel",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(fontPoppinsSemi),
                                color = Color.Gray
                            )
                        }
                    },
                    containerColor = Color.White,
                )
            }
        }
    }
}


@Composable
fun BottomCheckOut(
    cart: Cart,
    viewModel: CartViewModel,
    activity: Activity,
    pointUsed: Int,
    selectedPaymentMethod: String,
    deliveryAddress: String,
    noteText: String
) {
    if (cart.products.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Total", fontSize = 18.sp, fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Text(
                        text = "${cart.total}$",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                val paymentState by viewModel.paymentState.collectAsState()

                Button(
                    onClick = {
                        viewModel.updateCart()
                        viewModel.pay(discount = pointUsed, address = deliveryAddress, note = noteText,
                            paymentMethod = selectedPaymentMethod)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C7055))
                ) {
                    Text(
                        text = "Check Out",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                }

                when (paymentState) {
                    PaymentState.Loading -> {

                    }
                    PaymentState.Success -> {
                        ShowPaymentSuccessDialog(title = "Payment Success",
                            message = "Thanh toán thành công",
                            onDismissRequest = { viewModel.closeDialog() })

                    }

                    PaymentState.Cancel -> {
                        ShowPaymentSuccessDialog(title = "Payment Cancel",
                            message = "Thanh toán bị hủy",
                            onDismissRequest = { viewModel.closeDialog() })
                    }
                    PaymentState.Error -> {
                        ShowPaymentSuccessDialog(title = "Payment Error",
                            message = "Thanh toán không thành công",
                            onDismissRequest = { viewModel.closeDialog() })
                    }
                }

            }
        }
    }
}

@Composable
fun ShowPaymentSuccessDialog(
    title: String, message: String, onDismissRequest: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismissRequest, title = {
        Text(text = title)
    }, text = {
        Text(
            text = message
        )
    }, confirmButton = {
        TextButton(
            onClick = onDismissRequest
        ) {
            Text(text = "OK")
        }
    }
    )
}

@Composable
fun ItemOrder(
    product: ProductOfCart,
    amount: Int,
    onDeleteClicked: () -> Unit,
    onIncreaseClicked: () -> Unit,
    onDecreaseClicked: () -> Unit,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = backendImageRoute(product.product.image),
            placeholder = painterResource(id = R.drawable.ic_img_loading),
            error = painterResource(id = R.drawable.img_404_not_found),
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            product.product.name?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    fontSize = 17.sp,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.weight(2f), verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDecreaseClicked) {
                        Icon(
                            painterResource(R.drawable.iconminus),
                            contentDescription = "Decrease",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Text(
                        text = amount.toString(),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        modifier = Modifier.padding(horizontal = 4.dp) // Thêm padding ngang để tách các thành phần
                    )
                    IconButton(onClick = onIncreaseClicked) {
                        Icon(
                            painterResource(id = R.drawable.iconplus),
                            contentDescription = "Increase",
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
                Text(
                    text = "${product.price}$",
                    fontFamily = FontFamily(fontPoppinsSemi),
                    fontSize = 19.sp,
                    color = Color(0xFF331900),
                    modifier = Modifier.weight(1f)
                )
            }
        }
        IconButton(
            onClick = onDeleteClicked,
            modifier = Modifier.padding(start = 8.dp) // Thêm padding bên trái cho nút xóa
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}


