package com.example.learncode.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.learncode.R
import com.example.learncode.model.Product
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(navController: NavHostController, bottom: @Composable () -> Unit) {
    Box {
        Scaffold(
            topBar = { TopBarCenter(navController) }, containerColor = Color.Transparent, bottomBar = bottom
        ) { paddingValues ->
            ContentOrder(paddingValues = paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCenter(navController: NavHostController) {
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
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

@Composable
fun ContentOrder(paddingValues: PaddingValues) {
    val product = Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8)
    Box(modifier = Modifier
        .fillMaxSize()){
        LazyColumn (contentPadding = paddingValues){
            item {
                Column (modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Text(
                        text = "Delivery Address",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Tran Van Banh",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Text(
                        text = "ABC number, ABC street, ABC ward, ABC district, ABC city",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        color = Color.Gray
                    )
                    Row {
                        Button(onClick = {}, modifier = Modifier
                            .clip(RoundedCornerShape(20.dp)), colors = ButtonDefaults.buttonColors(containerColor = Color.White), border = BorderStroke(1.dp, Color.Gray)
                        )
                        {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "EditIcon", tint = Color.Black)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Edit Address", fontSize = 14.sp, color = Color.Black, fontFamily = FontFamily(fontPoppinsRegular))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Button(onClick = {}, modifier = Modifier
                            .clip(RoundedCornerShape(20.dp)), colors = ButtonDefaults.buttonColors(containerColor = Color.White), border = BorderStroke(1.dp, Color.Gray)
                        )
                        {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "AddNoteIcon", tint = Color.Black)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(text = "Add Note", fontSize = 14.sp, color = Color.Black, fontFamily = FontFamily(fontPoppinsRegular))
                        }
                    }
                    ItemOrder(product = product)
                    ItemOrder(product = product)
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Column (modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Text(
                        text = "Payment Summary",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Subtotal",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(fontPoppinsRegular)
                        )
                        Text(
                            text = "2.80$",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(fontPoppinsSemi)
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
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
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Discount",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(fontPoppinsRegular)
                        )
                        Text(
                            text = "0$",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(fontPoppinsSemi)
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Column (modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(10.dp)){
                    Text(
                        text = "Payment Method",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Cash",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(fontPoppinsRegular)
                        )
                        RadioButton(selected = true, onClick = {  })
                    }
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Momo",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(fontPoppinsRegular)
                        )
                        RadioButton(selected = false, onClick = {  })
                    }
                    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Banking",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(fontPoppinsRegular)
                        )
                        RadioButton(selected = false, onClick = {  })
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                BottomCheckOut()
            }
        }
    }
}

@Composable
fun BottomCheckOut() {
    Box (modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(10.dp)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(
                    text = "Total",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(fontPoppinsSemi)
                )
                Text(
                    text = "2.80$",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(fontPoppinsSemi)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9C7055))) {
                Text(text = "Check Out", fontSize = 18.sp, fontFamily = FontFamily(fontPoppinsSemi))
            }
        }
    }
}

@Composable
fun ItemOrder(product: Product) {
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
            painter = painterResource(id = product.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.width(10.dp))
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(
                horizontalAlignment = Alignment.Start) {
                Text(
                    text = product.title,
                    maxLines = 1,
                    fontSize = 17.sp,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = product.des,
                    fontFamily = FontFamily(fontPoppinsRegular),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Row (verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "IconDown")
                    }
                    Text(modifier = Modifier.offset(y = 2.dp), text = "1", fontSize = 18.sp, fontFamily = FontFamily(fontPoppinsRegular))
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "IconTop")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product.price,
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