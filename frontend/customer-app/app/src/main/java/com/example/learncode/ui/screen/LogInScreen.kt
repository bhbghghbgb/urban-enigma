package com.example.learncode.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.learncode.R
import com.example.learncode.ui.components.CustomTextField
import com.example.learncode.ui.components.CustomTextFieldNumber
import com.example.learncode.ui.components.FilledButtonExample
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi
import com.example.learncode.viewmodel.AuthViewModel

@Composable
fun LogInScreen(navController: NavHostController) {
    val viewModel = remember { AuthViewModel() }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val phoneNumberError by viewModel.phoneNumberError.observeAsState("")
    val passwordError by viewModel.passwordError.observeAsState("")

    val navigateToHome by viewModel.navigateToHome.observeAsState()
    val isInvalidDataDialogVisible by viewModel.isInvalidDataDialogVisible.observeAsState()
    if (navigateToHome == true) {
        navController.navigate("homescreen")
    }

    viewModel.setContext(LocalContext.current)

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
                painter = painterResource(id = R.drawable.iconcoffee),
                contentDescription = null,
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
            text = "Log In",
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
                viewModel.clearPhoneNumberError()
            },
            errorText = phoneNumberError
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
                viewModel.clearPasswordError()
            },
            errorText = passwordError
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButtonExample(
            onClick = {
                viewModel.login(phoneNumber, password)
            },
            text = "LOGIN",
            backgroundColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF04764E)
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Don't have account",
            fontFamily = FontFamily(fontPoppinsSemi),
            color = Color(0xFF8A8A8A),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        FilledButtonExample(
            onClick = { navController.navigate("register") },
            text = "CREATE ACCOUNT",
            backgroundColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEDE0CF)
            )
        )
    }

    if (isInvalidDataDialogVisible == true) {
        CustomAlert(viewModel)
    }
}

@Composable
fun CustomAlert(viewModel: AuthViewModel) {
    Surface(
        color = Color.Black.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.Transparent,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissInvalidDataDialog() },
                title = {
                    Text(
                        text = "Invalid Data",
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                },
                text = {
                    Text(
                        text = "Please enter a valid phone number and password.",
                        color = Color.Black,
                        fontFamily = FontFamily(fontPoppinsRegular)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { viewModel.dismissInvalidDataDialog() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9c7055)),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "OK",
                            color = Color.White
                        )
                    }
                },
                containerColor = Color.White,
                textContentColor = Color.Transparent
            )
        }
    }
}