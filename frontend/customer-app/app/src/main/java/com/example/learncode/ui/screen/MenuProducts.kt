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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.learncode.model.Products
import com.example.learncode.ui.components.FilterChipShow
import com.example.learncode.ui.components.ItemView
import com.example.learncode.ui.components.ItemViewRow
import com.example.learncode.ui.components.SearchView
import com.example.learncode.ui.theme.fontPoppinsSemi
import com.example.learncode.viewmodel.SearchViewModel
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
    // Tạo biến để lưu trữ giá trị của TextField
    var searchText by remember { mutableStateOf("") }

    // Khai báo viewModel ở ngoài Column để tránh việc tạo mới nó mỗi khi recompose
    val viewModel = remember {
        SearchViewModel()
    }

    Column(
        Modifier
            .padding(paddingValues)
            .background(Color.White)
    ) {
        // Truyền giá trị của TextField từ biến searchText vào SearchView
        SearchView(
            navController,
            Modifier.focusRequester(focusRequester),
            isNavigated,
            {},
            {
                // Khi click vào Floating Action Button, lấy giá trị của TextField và truyền vào hàm fetchData
                viewModel.fetchData(searchText)
            }
        ) { text ->
            searchText = text // Cập nhật giá trị của TextField khi nó thay đổi
        }

        // Sử dụng LaunchedEffect để gọi fetchData khi productList rỗng
        LaunchedEffect(Unit) {
            if (viewModel.productList.value.isNullOrEmpty()) {
                viewModel.fetchData(searchText)
            }
        }

        // Đợi 300ms sau khi isNavigated thay đổi, sau đó yêu cầu focus cho view
        LaunchedEffect(isNavigated) {
            if (isNavigated) {
                delay(300)
                focusRequester.requestFocus()
            }
        }

        val productLists by viewModel.productList.observeAsState(emptyList())

        CategorySection()
        // RenderProduct()
        RenderProductList(navController, productLists)

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
fun RenderProductList(navController: NavController, productLists: List<Products>) {
    LazyColumn(
        Modifier.padding(16.dp, 0.dp, 16.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
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