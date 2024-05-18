package com.doansgu.cafectm.ui.screen


import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.doansgu.cafectm.R
import com.doansgu.cafectm.model.AddToCartRequest
import com.doansgu.cafectm.ui.components.CustomToast
import com.doansgu.cafectm.ui.components.IconButtonCustom
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.util.backendImageRoute
import com.doansgu.cafectm.viewmodel.CartViewModel
import com.doansgu.cafectm.viewmodel.DetailViewModel
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, id: String) {
    val viewModel = DetailViewModel()
    val carViewModel = CartViewModel()
    val product by viewModel.product.observeAsState()
    var showToast by remember { mutableStateOf(false) }

//    LaunchedEffect(id) {
        viewModel.fetchData(id)
//    }
    val scollState = rememberLazyListState()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (product == null) {
            LazyColumn(
                contentPadding = PaddingValues(top = 420.dp),
                modifier = Modifier.background(White).fillMaxSize(),
            ) {
                item {
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
                }
            }
        } else {
            Scaffold(bottomBar = {
                AddtoCart(onClick = {
                    carViewModel.addToCart(AddToCartRequest(id))
                    showToast = true
                })
            }) {
                Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                    Content(scollState, product?.description, product?.rating, product?.price)
                    TopBarDetail(scollState, navController, product?.name, product?.image)

                    if (showToast) {
                        LaunchedEffect(showToast) {
                            delay(2000)
                            showToast = false
                        }
                        CustomToast(message = "Add to cart succesfully!!!")
                    }
                }
            }
        }
    }
}

@Composable
fun TopBarDetail(scrollState: LazyListState, navController: NavController, name: String?, image: String?) {
    val imageHeight = 350.dp
//    val screenHeight = LocalConfiguration.current.screenHeightDp

    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)

    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = White,
        modifier = Modifier
            .height(420.dp)
            .offset { IntOffset(x = 0, y = -offset) },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box(
                Modifier
                    .height(imageHeight)
                    .background(Color.White)
                    .graphicsLayer {
                        alpha = 1f - offsetProgress
                    }) {
                AsyncImage(
                    model = backendImageRoute(image),
                    placeholder = painterResource(id = R.drawable.ic_img_loading),
                    error = painterResource(id = R.drawable.img_404_not_found),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.4f, Transparent), Pair(1f, White)
                                )
                            )
                        )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (name !== null) name else "null",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)

                )
            }
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(70.dp)
            .padding(16.dp)
    ) {
        CircularButton(R.drawable.iconback) {
            navController.popBackStack()
        }
//        CircularButton(R.drawable.iconheart)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResouce: Int, color: Color = Color.LightGray, onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = color),
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(
            modifier = Modifier.size(width = 30.dp, height = 30.dp),
            painter = painterResource(id = iconResouce),
            contentDescription = null
        )
    }
}

@Composable
fun Content(scrollState: LazyListState, description: String?, star: Double?, price: Double?) {
    LazyColumn(
        contentPadding = PaddingValues(top = 420.dp),
        modifier = Modifier.background(White),
        state = scrollState
    ) {
        item {
            StarReport(star, price)
            DescriptionProduct(des = description)
        }
    }
}

@Composable
fun PriceQuantity() {
    var value = 0
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.LightGray)

    ) {
        Row(
            modifier = Modifier.padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularButton(iconResouce = R.drawable.iconminus, color = Color.LightGray) { value-- }
            Text(
                text = "$value",
                Modifier.padding(16.dp),
                fontSize = 18.sp,
                fontFamily = FontFamily(fontPoppinsSemi)
            )
            CircularButton(iconResouce = R.drawable.iconplus, color = Color.LightGray) { value++ }
        }
        Text(
            text = "2.00$",
            fontSize = 25.sp,
            fontFamily = FontFamily(fontPoppinsRegular),
            modifier = Modifier.padding(end = 5.dp)
        )
    }
}

@Composable
fun AddtoCart(onClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        IconButtonCustom(
            onClick = onClick,
            text = "Add to Card",
            backgroundColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C7055)
            ),
            border = BorderStroke(width = 0.dp, color = Color(0xFF9C7055)),
            icon = R.drawable.iconcart,
            fontcolor = Color.White
        )
    }
}

@Composable
fun StarReport(star: Double?, price: Double?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = star.toString(),
                fontSize = 20.sp,
                fontFamily = FontFamily(fontPoppinsRegular)
            )
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(width = 25.dp, height = 25.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.iconstar),
                    contentDescription = null,
                    tint = Color.Yellow
                )
                Icon(
                    modifier = Modifier
                        .size(width = 25.dp, height = 25.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.iconstar),
                    contentDescription = null,
                    tint = Color.Yellow
                )
                Icon(
                    modifier = Modifier
                        .size(width = 25.dp, height = 25.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.iconstar),
                    contentDescription = null,
                    tint = Color.Yellow
                )
                Icon(
                    modifier = Modifier
                        .size(width = 25.dp, height = 25.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.iconstar),
                    contentDescription = null,
                    tint = Color.Yellow
                )
                Icon(
                    modifier = Modifier
                        .size(width = 25.dp, height = 25.dp)
                        .padding(1.dp),
                    painter = painterResource(id = R.drawable.iconstar),
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }
        }
        Text(
            text = "$" + price,
            fontSize = 28.sp,
            color = Color(0xFF3B210A),
            fontFamily = FontFamily(fontPoppinsRegular),
            modifier = Modifier.padding(end = 5.dp)
        )
    }
}

@Composable
fun TextTitle() {
    Text(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        text = "Size",
        fontSize = 20.sp,
        fontFamily = FontFamily(fontPoppinsSemi)
    )
}

@Composable
fun DescriptionProduct(des: String?) {
    Text(
        text = if (des !== null) des else "null",
        fontFamily = FontFamily(fontPoppinsRegular),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun SizeChoose() {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEDE0CF))
            .fillMaxWidth()
            .height(40.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        TabButton("Small", true, Modifier.weight(1f))
        TabButton("Medium", false, Modifier.weight(1f))
        TabButton("Large", false, Modifier.weight(1f))
    }
}

@Composable
fun TabButton(text: String, active: Boolean, modifier: Modifier) {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxHeight(),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9C7055), contentColor = Color.White
        ) else ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEDE0CF), contentColor = Color.Black
        )
    ) {
        Text(text)
    }
}

@Composable
fun RatingBar(
    rating: Int, onRatingChanged: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        (1..5).forEach { star ->
            IconButton(
                onClick = { onRatingChanged(star) }, modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (star <= rating) {
                        Icons.Outlined.Star
                    } else {
                        Icons.Filled.Star
                    }, contentDescription = null
                )
            }
        }
    }
}