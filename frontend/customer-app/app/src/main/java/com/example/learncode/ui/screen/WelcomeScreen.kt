package com.example.learncode.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.learncode.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.coffee_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(width = 150.dp, height = 150.dp)
            )
            Text(
                text = "CoffeeShop",
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                fontWeight = FontWeight(weight = 600),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Coffee Shop App",
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                color = Color.Gray,
                fontWeight = FontWeight(1000)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Morning begin with\ncoffee shop",
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(50.dp))
            Column(

                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 24.dp)

            ) {
                GmailButton()
                Spacer(modifier = Modifier.height(20.dp))
                NumberButton(navController)
            }

        }
    }
}

@Composable
fun EmailButton() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(24, 119, 242),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
            .padding(0.dp, 10.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.facebook_icon), contentDescription = "",
            alignment = Alignment.CenterStart
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Login with Facebook",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun GmailButton() {
    Button(
        onClick = {},
        border = BorderStroke(width = 2.dp, color = Color.Gray),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.google_icon), contentDescription = "",
            alignment = Alignment.CenterStart
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Login with Google",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
    }
}

@Composable
fun NumberButton(navController: NavHostController) {
    Button(
        onClick = {
            navController.navigate("login")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF04764E),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp),
        shape = RoundedCornerShape(50.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.phone_icon), contentDescription = "",
            alignment = Alignment.CenterStart
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Login with Numbers",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semibold)),
                textAlign = TextAlign.Center
            )
        }

    }
}
