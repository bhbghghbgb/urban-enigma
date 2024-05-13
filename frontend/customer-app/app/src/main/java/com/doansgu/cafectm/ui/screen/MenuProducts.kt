package com.doansgu.cafectm.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doansgu.cafectm.model.Product2
import com.doansgu.cafectm.ui.components.FilterChipShow
import com.doansgu.cafectm.ui.components.ItemViewRow2
import com.doansgu.cafectm.ui.components.SearchView
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.viewmodel.SearchViewModel

@Composable
fun MenuProducts(
    navController: NavController, focusRequester: FocusRequester, viewModel: SearchViewModel
) {
    val isNavigated by rememberSaveable { mutableStateOf(false) }
    Box {
        Scaffold(
            topBar = { TopBar(navController, viewModel) }, containerColor = Color.Transparent
        ) { paddingValues ->
            Content(
                paddingValues = paddingValues, navController, focusRequester, isNavigated, viewModel
            )
        }
    }
}

@Composable
fun Content(
    paddingValues: PaddingValues,
    navController: NavController,
    focusRequester: FocusRequester,
    isNavigated: Boolean,
    viewModel: SearchViewModel
) {
    Column(
        Modifier
            .padding(paddingValues)
            .background(Color.White)
    ) {
        val productLists by viewModel.productList.observeAsState(emptyList())
//        CategorySection()
        RenderProductList(navController, productLists, focusRequester, isNavigated, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, viewModel: SearchViewModel) {
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
                viewModel.resetProducts()
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
            contentPadding = PaddingValues(16.dp, 4.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filterChipTexts) { item ->
                FilterChipShow(title = item, selected = selected, onSelected = { selected = item })
            }
        }
    }
}

@Composable
fun RenderProductList(
    navController: NavController,
    productLists: List<Product2>,
    focusRequester: FocusRequester,
    isNavigated: Boolean,
    viewModel: SearchViewModel
) {
    var searchText by remember { mutableStateOf("") }
    LazyColumn(
        Modifier
            .padding(0.dp, 0.dp, 0.dp, 0.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            SearchView(navController, isNavigated, {
                viewModel.fetchData(searchText)
            }, { text ->
                searchText = text
            }, focusRequester
            )
        }
        item { CategorySection() }
        if (productLists.isNotEmpty()) {
            item { Spacer(modifier = Modifier.height(2.dp)) }
            productLists.forEach { product ->
                item {
                    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                        ItemViewRow2(
                            name = product.name,
                            image = /*R.drawable.mocha,*/ null,
                            description = product.description,
                            price = product.price,
                            rating = product.rating,
                            navController = navController
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
        }
    }
}