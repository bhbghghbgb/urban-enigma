package com.example.learncode.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learncode.R
import com.example.learncode.model.Product
import com.example.learncode.ui.components.FilterChipShow
import com.example.learncode.ui.components.ItemView
import com.example.learncode.ui.components.ItemViewRow
import com.example.learncode.ui.components.SearchView
import com.example.learncode.ui.theme.fontPoppinsSemi
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuProducts(navController: NavController) {
    val isNavigated by rememberSaveable { mutableStateOf(false) }
    Box {
        Scaffold(
            topBar = { TopBar(navController) }, containerColor = Color.Transparent
        ) { paddingValues ->
            val focusRequester = remember {
                FocusRequester()
            }
            Content(paddingValues = paddingValues, navController, focusRequester, isNavigated)
        }
    }
}

@Composable
fun Content(paddingValues: PaddingValues, navController: NavController, focusRequester: FocusRequester, isNavigated: Boolean) {
    Column(
        Modifier
            .padding(paddingValues)
            .background(Color.White)) {

        SearchView(navController, Modifier.focusRequester(focusRequester), isNavigated)
        LaunchedEffect(isNavigated) {
            if (isNavigated) {
                delay(300)
                focusRequester.requestFocus()
            }
        }

        CategorySection()
//        RenderProduct()
        RenderProductList(navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        TopAppBar(colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF6A4731)
        ), title = {
            Column {
                Text(
                    "Delivery Address",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    fontSize = 14.sp,
                )
                Text(
                    "Abc street, abc district, abc city",
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    fontFamily = FontFamily(fontPoppinsSemi)
                )
            }
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "BackIcon",
                    tint = Color.White
                )
            }
        }, actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Localized description",
                    tint = Color.White
                )
            }
        })
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySection() {
    val filterChipTexts: List<String> =
        listOf("All", "Coffee", "Milk tea", "Fruit tea", "Soda", "Ice")
    var selected by remember { mutableStateOf(filterChipTexts[0]) }
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Category", fontSize = 18.sp, fontFamily = FontFamily(fontPoppinsSemi))
        }
        LazyRow(
            contentPadding = PaddingValues(16.dp, 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filterChipTexts) { item ->
                FilterChipShow(title = item, selected = selected, onSelected = { selected = item })
            }
        }
    }
}

@Composable
fun RenderProduct(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            val product = Product(
                1,
                R.drawable.affogato,
                "Affogato",
                "with coffee and milk sugar",
                "1.99$",
                4.5
            )
            ItemView(
                title = product.title,
                image = product.image,
                des = product.des,
                price = product.price,
                star = product.star,
                navController = navController
            )
        }
        item {
            val product = Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8)
            ItemView(
                title = product.title,
                image = product.image,
                des = product.des,
                price = product.price,
                star = product.star,
                navController = navController
            )
        }
        item {
            val product = Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
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

@Composable
fun RenderProductList(navController: NavController) {
    LazyColumn(
        Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
//            val product = Product(
//                1,
//                R.drawable.affogato,
//                "Affogato",
//                "with coffee and milk sugar",
//                "1.99$",
//                4.5
//            )
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(
//                1,
//                R.drawable.affogato,
//                "Affogato",
//                "with coffee and milk sugar",
//                "1.99$",
//                4.5
//            )
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(
//                1,
//                R.drawable.affogato,
//                "Affogato",
//                "with coffee and milk sugar",
//                "1.99$",
//                4.5
//            )
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(2, R.drawable.mocha, "Mocha", "with milk", "1.40$", 4.8)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//        item {
//            val product = Product(3, R.drawable.latte, "Latte", "with coffee", "1.25$", 5.0)
//            ItemViewRow(
//                title = product.title,
//                image = product.image,
//                des = product.des,
//                price = product.price,
//                star = product.star,
//                navController = navController
//            )
//        }
//    }
        }}
}