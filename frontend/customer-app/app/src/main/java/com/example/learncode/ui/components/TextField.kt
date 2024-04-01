package com.example.learncode.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.learncode.ui.theme.fontPoppinsRegular

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldNumber(placeholder: String) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {newValue ->
            if (newValue.isDigitsOnly()) {
                text = newValue
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                fontFamily = FontFamily(fontPoppinsRegular),
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(fontPoppinsRegular)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(placeholder: String, isPassword: Boolean) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { text = it
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                fontFamily = FontFamily(fontPoppinsRegular),
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(fontPoppinsRegular)
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}
