package com.doansgu.cafectm.ui.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doansgu.cafectm.R
import com.doansgu.cafectm.model.Product
import com.doansgu.cafectm.ui.components.ImageSliderWithIndicator
import com.doansgu.cafectm.ui.components.ItemView
import com.doansgu.cafectm.ui.components.ItemViewRow
import com.doansgu.cafectm.ui.components.SearchView
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.viewmodel.HomeViewModel

val listPoster = listOf(
    R.drawable.poster4,
    R.drawable.poster5,
    R.drawable.postercoffee3
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, bottom: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
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
                tint = Color.White
            )
        }
    }
}

@Composable
fun ContentHome(
    paddingValues: PaddingValues,
    navController: NavController
) {
    val viewModel = remember { HomeViewModel() }

    if (viewModel.productList.value.isNullOrEmpty()) {
        LaunchedEffect(Unit) {
            viewModel.fetchData()
        }
    }
    val productLists by viewModel.productList.observeAsState(emptyList())
    val stateCallAllPopular by viewModel.stateCallFullPopular.observeAsState(false)
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
                        val keyboardController = LocalSoftwareKeyboardController.current
                        keyboardController?.hide()
                        val onclick: () -> Unit = {}
                        SearchView(navController, false, onclick, {
                            navController.navigate("menuproduct") {
                                launchSingleTop = true
                            }
                        }, null)
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
                        Row(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                text = "Popular Menu",
                                fontSize = 20.sp,
                                fontWeight = FontWeight(1000),
                                fontFamily = FontFamily(fontPoppinsSemi),
                                color = Color.Black
                            )
                        }
                        if (!stateCallAllPopular) {
                            Row() {
                                TextButton(onClick = {
                                    viewModel.fetchFullData()
                                }) {
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
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    if (productLists.isEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(35.dp),
                                color = MaterialTheme.colorScheme.secondary, // Sử dụng MaterialTheme.colors.secondary thay vì MaterialTheme.colorScheme.secondary
                                backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    } else {
                        productLists.forEach { product ->
                            ItemViewRow(
                                _id = product._id,
                                title = product.name,
                                image = R.drawable.mocha,
                                des = product.description,
                                price = product.price,
                                star = product.avgRating,
                                navController = navController
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}
