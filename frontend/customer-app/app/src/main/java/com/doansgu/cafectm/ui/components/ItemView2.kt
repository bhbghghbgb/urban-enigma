package com.doansgu.cafectm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.doansgu.cafectm.R
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.util.backendImageRoute

@Preview
@Composable
fun ItemView2Preview() {
    ItemView2(
        name = "Tra sua",
        image = null,
        description = "Tra sua khong duong",
        price = 6996.0,
        rating = 0.0,
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun ItemViewRow2Preview() {
    ItemViewRow2(
        name = "Tra sua",
        image = null,
        description = "Tra sua khong duong",
        price = 6996.0,
        rating = 0.0,
        navController = rememberNavController()
    )
}

@Composable
fun ItemView2(
    name: String?,
    image: String?,
    description: String?,
    price: Double?,
    rating: Double?,
    navController: NavController
) {
    ElevatedCard(
        modifier = Modifier
            .width(180.dp)
            .wrapContentHeight()
            .clickable {
                navController.navigate("detail")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Box {
                AsyncImage(
                    model = backendImageRoute(image),
                    placeholder = painterResource(id = R.drawable.ic_img_loading),
                    error = painterResource(id = R.drawable.img_404_not_found),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .height(120.dp),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 16.dp))
                        .background(Color(0xFF6A4731))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "StarIcon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = rating.toString(), fontSize = 14.sp, color = Color.White)
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                Text(
                    text = name ?: "null",
                    maxLines = 1,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(fontPoppinsSemi),
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = description ?: "null",
                    fontFamily = FontFamily(fontPoppinsRegular),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp,
                    color = Color.Gray,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = price.toString(),
                        fontFamily = FontFamily(fontPoppinsSemi),
                        fontSize = 25.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF331900)
                    )
                    IconButton(onClick = {}, modifier = Modifier.size(48.dp)) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "IconAdd",
                            tint = Color(0xFF6A4731)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemViewRow2(
    name: String?,
    image: String?,
    description: String?,
    price: Double?,
    rating: Double?,
    navController: NavController
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable {
                navController.navigate("detail")
            }, shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = backendImageRoute(image),
                placeholder = painterResource(id = R.drawable.ic_img_loading),
                error = painterResource(id = R.drawable.img_404_not_found),
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = name ?: "null",
                        maxLines = 1,
                        fontSize = 17.sp,
                        fontFamily = FontFamily(fontPoppinsSemi),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = description ?: "null",
                        fontFamily = FontFamily(fontPoppinsRegular),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = Color.Gray,
                    )
                    Text(
                        text = price.toString(),
                        fontFamily = FontFamily(fontPoppinsSemi),
                        fontSize = 19.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF331900)
                    )
                }
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "StarIcon",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = rating.toString(), fontSize = 16.sp, color = Color.Black)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(onClick = {}, modifier = Modifier.size(40.dp)) {
                        Icon(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "IconAdd",
                            tint = Color(0xFF6A4731)
                        )
                    }
                }
            }
        }
    }
}