package com.example.learncode.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.learncode.R
import com.example.learncode.model.Product
import com.example.learncode.ui.components.ImageSliderWithIndicator
import com.example.learncode.ui.components.ItemView
import com.example.learncode.ui.components.ItemViewRow
import com.example.learncode.ui.components.SearchView
import com.example.learncode.ui.theme.fontPoppinsSemi

val listPoster = listOf(
    R.drawable.poster4,
    R.drawable.poster5,
    R.drawable.postercoffee3
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, bottom: @Composable () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

    }
    Scaffold(
        topBar = {
            TopBarHome()
        },
        containerColor = Color.Transparent,
        bottomBar = bottom
    ) { paddingValues ->
        ContentHome(paddingValues, navController)
    }

}

@Composable
fun TopBarHome() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF9C7055))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .background(Color(0xFF9C7055)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(width = 40.dp, height = 40.dp),
                    painter = painterResource(id = R.drawable.iconcoffee),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "NewLands",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000),
                    fontFamily = FontFamily(fontPoppinsSemi),
                    color = Color.White
                )
            }
            Icon(
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.iconbell),
                contentDescription = null,
                tint = Color.White,

                )

        }
    }
}

@Composable
fun ContentHome(paddingValues: PaddingValues, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = paddingValues
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(85.dp)
                        .offset(y = (-10).dp)
                        .background(Color.White)

                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .clip(
                                RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                            )
                            .background(Color(0xFF9C7055))
                    ) {

                    }
                    Row(
                    ) {
                        SearchView(navController, Modifier, false)
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    ImageSliderWithIndicator(imageList = listPoster)
                }
            }
            item {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Text(
                                text = "Special For You!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight(1000),
                                fontFamily = FontFamily(fontPoppinsSemi),
                                color = Color.Black
                            )
                        }
                        Row() {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "View All",
                                    color = Color(0xFF9C7055)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "",
                                    tint = Color(0xFF9C7055)
                                )
                            }
                        }
                    }
                }
            }
            item {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val itemList = listOf(
                        Product(
                            1,
                            R.drawable.affogato,
                            "Affogato",
                            "with coffee and milk sugar",
                            "1.99$",
                            4.5
                        ),
                        Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8),
                        Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
                    )
                    itemList.forEach { product ->
                        item {
                            ItemView(
                                title = product.title,
                                image = product.image,
                                des = product.des,
                                price = product.price,
                                star = product.star,
                                navController = navController
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Text(
                                text = "Popular Menu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight(1000),
                                fontFamily = FontFamily(fontPoppinsSemi),
                                color = Color.Black
                            )
                        }
                        Row() {
                            TextButton(onClick = { /*TODO*/ }) {
                                Text(
                                    text = "View All",
                                    color = Color(0xFF9C7055)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "",
                                    tint = Color(0xFF9C7055)
                                )
                            }
                        }
                    }
                }
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    val itemList = listOf(
                        Product(
                            1,
                            R.drawable.affogato,
                            "Affogato",
                            "with coffee and milk sugar",
                            "1.99$",
                            4.5
                        ),
                        Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8),
                        Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
                    )
                    itemList.forEach { product ->
                        ItemViewRow(
                            title = product.title,
                            image = product.image,
                            des = product.des,
                            price = product.price,
                            star = product.star,
                            navController = navController
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController()) {

    }
}