package com.example.learncode.ui.screen

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learncode.R
import com.example.learncode.model.NavigationItem
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopBarProfile(navController)
            },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            ContentProfile(paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(navController: NavController) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White,
        ),
        title = {
            Text(
                "Profile",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(fontPoppinsSemi),
            )
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
    )
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(70.dp)
//            .background(Color.White)
//            .padding(horizontal = 10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Text(
//            modifier = Modifier.padding(start = 5.dp),
//            text = "Profile",
//            fontSize = 20.sp,
//            fontWeight = FontWeight(600),
//            fontFamily = FontFamily(fontPoppinsSemi),
//        )
//        IconButton(onClick = {}) {
//            Icon(
//                modifier = Modifier.size(width = 40.dp, height = 40.dp),
//                painter = painterResource(id = R.drawable.iconcoffee),
//                contentDescription = null,
//                tint = Color.Black
//            )
//        }
//
//    }
}

@Composable
fun ContentProfile(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 70.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Image(
            painter = painterResource(id = R.drawable.affogato),
            contentDescription = null,
            modifier = Modifier
                .size(height = 120.dp, width = 120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,

        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = "William Smith",
            fontSize = 30.sp,
            fontFamily = FontFamily(fontPoppinsSemi)
        )
        Text(
            modifier = Modifier.padding(top = 5.dp),
            text = "London, England",
            fontSize = 20.sp,
            fontFamily = FontFamily(fontPoppinsRegular)
        )
        Column(
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(horizontal = 30.dp)
        ) {
            ItemProfile(image = R.drawable.iconphone, title = "Mobile Phone", des = "+84 0123456789")
//            Spacer(modifier = Modifier.height(5.dp))
            ItemProfile(image = R.drawable.iconemail, title = "Email Address", des = "abc@gmail.com")
//            Spacer(modifier = Modifier.height(5.dp))
            ItemProfile(image = R.drawable.iconlocation, title = "Address", des = "Franklin Avenue")
//            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun ItemProfile(image: Int, title: String, des: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedCard(
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp),
                    painter = painterResource(id = image),
                    contentDescription = "",
                    tint = Color(0xFF9C7055)
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontFamily = FontFamily(fontPoppinsRegular),
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = des,
                fontFamily = FontFamily(fontPoppinsSemi),
                fontSize = 16.sp,
            )
        }
    }
}