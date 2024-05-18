package com.doansgu.cafectm.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.doansgu.cafectm.R
import com.doansgu.cafectm.ui.components.ImageSliderWithIndicator
import com.doansgu.cafectm.ui.components.ItemView2
import com.doansgu.cafectm.ui.components.ItemViewRow2
import com.doansgu.cafectm.ui.components.SearchView
import com.doansgu.cafectm.ui.components.TopBarHome
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.viewmodel.HomeScreen2ViewModel

@Preview
@Composable
fun HomeScreen2Preview() {
    HomeScreen2(navController = rememberNavController(), viewModel = viewModel(), bottom = {})
}

@Composable
fun HomeScreen2(
    navController: NavHostController,
    viewModel: HomeScreen2ViewModel,
    bottom: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarHome()
        }, bottomBar = bottom
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn() {
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
                        ) {}
                        Row {
                            SearchView(navController, false, {
                                navController.navigate("menuproduct") {
                                    launchSingleTop = true
                                }
                            }, {}, null)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .padding(horizontal = 16.dp)
                    ) {
                        ImageSliderWithIndicator(
                            imageList = listOf(
                                R.drawable.poster4, R.drawable.poster5, R.drawable.postercoffee3
                            )
                        )
                    }
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Text(
                                    text = "Special For You!",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(1000),
                                    fontFamily = FontFamily(fontPoppinsSemi),
                                    color = Color.Black
                                )
                            }
                            Row {
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text(
                                        text = "View All", color = Color(0xFF9C7055)
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "",
                                        tint = Color(0xFF9C7055)
                                    )
                                }
                            }
                        }
                    }
                    val productList by viewModel.productList.observeAsState(emptyList())
                    LazyRow(
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(productList) { product ->
                            ItemView2(
                                name = product.name,
                                image = product.image,
                                description = product.description,
                                price = product.price,
                                rating = product.rating,
                                navController = navController,
                            )
                        }
//                    items {
//                        productList.forEach {
//                            ItemView2(
//                                name = it.name,
//                                image = it.image,
//                                description = it.description,
//                                price = it.price,
//                                rating = it.rating,
//                                navController = navController,
//                            )
//                        }
//                    }
                    }
                    Box(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row {
                                Text(
                                    text = "Popular Menu",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight(1000),
                                    fontFamily = FontFamily(fontPoppinsSemi),
                                    color = Color.Black
                                )
                            }
                            Row {
                                TextButton(onClick = { /*TODO*/ }) {
                                    Text(
                                        text = "View All", color = Color(0xFF9C7055)
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
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
//                        item {
                        productList.forEach {
                            ItemViewRow2(
                                name = it.name,
                                image = it.image,
                                description = it.description,
                                price = it.price,
                                rating = it.rating,
                                navController = navController,
                            )
                        }
//                        }
                    }
                }
            }
        }
    }
}