package com.doansgu.cafectm.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doansgu.cafectm.R
import com.doansgu.cafectm.ui.components.CustomTextField
import com.doansgu.cafectm.ui.components.CustomTextFieldNumber
import com.doansgu.cafectm.ui.components.FilledButtonExample
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel = remember { AuthViewModel() }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumberErrorText by remember { mutableStateOf("") }
    var passwordErrorText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.iconcoffee), contentDescription = null,
                modifier = Modifier.size(width = 50.dp, height = 50.dp),
                tint = Color.Black
            )
            Text(
                text = "NewLands",
                fontFamily = FontFamily(fontPoppinsSemi),
                color = Color.Black,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Create an account",
            fontFamily = FontFamily(fontPoppinsSemi),
            color = Color.Black,
            fontSize = 24.sp
        )
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur\n adipiscing elit, sed do eiusmod tempor ",
            fontFamily = FontFamily(fontPoppinsSemi),
            color = Color.Black,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Phone number",
            fontFamily = FontFamily(fontPoppinsSemi),
            color = Color(0xFF8A8A8A),
            fontSize = 14.sp
        )
        CustomTextFieldNumber(
            placeholder = "Enter Phone number",
            onValueChanged = {
                phoneNumber = it
                phoneNumberErrorText = ""
            },
            errorText = phoneNumberErrorText
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Password",
            fontFamily = FontFamily(fontPoppinsSemi),
            color = Color(0xFF8A8A8A),
            fontSize = 14.sp
        )
        CustomTextField(
            placeholder = "Enter Password",
            isPassword = true,
            onValueChanged = {
                password = it
                passwordErrorText = ""
            },
            errorText = passwordErrorText
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButtonExample(
            onClick = { /*TODO*/ }, text = "SIGN UP", backgroundColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF04764E)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}