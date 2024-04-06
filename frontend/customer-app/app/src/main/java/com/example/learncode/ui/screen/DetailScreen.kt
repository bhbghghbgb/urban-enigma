package com.example.learncode.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.*


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.example.learncode.R
import com.example.learncode.model.NavigationItem
import com.example.learncode.ui.components.IconButtonCustom
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun DetailScreen(navController: NavController) {
    val scollState = rememberLazyListState()
    Box(modifier = Modifier
        .fillMaxSize())
//        .wrapContentHeight()) {
        {
        Content(scollState)
        TopBarDetail(scollState, navController)
    }
}

@Composable
fun TopBarDetail(scrollState: LazyListState, navController: NavController) {
    val imageHeight = 350.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp

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
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mocha),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.4f, Transparent),
                                    Pair(1f, White)
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
                    text = "Affogato",
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
            navController.navigate(NavigationItem.Home.route)
        }

        CircularButton(R.drawable.iconheart)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResouce: Int,
    color: Color = Color.LightGray,
    onClick: () -> Unit = {}
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
            contentDescription = null)
    }
}

@Composable
fun Content(scrollState: LazyListState) {
    LazyColumn(

        contentPadding = PaddingValues(top = 420.dp),
        modifier = Modifier.background(White),
        state = scrollState
    ) {
        item {
            StarReport()
            DescriptionProduct(des = "Affogato is a beverage made from coffee. It is usually prepared with a scoop of vanilla gelato or ice cream on top of a cup of hot espresso. Some other ways to prepare include using a cup of Amaretto or other flavored wine")
            TextTitle()
            SizeChoose()
            TextTitle()
            PriceQuantity()
            TextTitle()
            SizeChoose()
            TextTitle()
            PriceQuantity()
            TextTitle()
            SizeChoose()
            TextTitle()
            PriceQuantity()
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
            Text(text = "$value", Modifier.padding(16.dp), fontSize = 18.sp, fontFamily = FontFamily(fontPoppinsSemi))
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
fun AddtoCard() {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        IconButtonCustom(
            onClick = {},
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
fun StarReport() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Text(
                text = "5.0",
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
            text = "2.00$",
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
fun DescriptionProduct(des: String) {
    Text(
        text = des,
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
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
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
        modifier = modifier
            .fillMaxHeight(),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9C7055),
            contentColor = Color.White
        ) else ButtonDefaults.buttonColors(
            containerColor = Color(0xFFEDE0CF),
            contentColor = Color.Black
        )
    ) {
        Text(text)
    }
}