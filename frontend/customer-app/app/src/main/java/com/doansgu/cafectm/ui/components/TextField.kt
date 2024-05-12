package com.doansgu.cafectm.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular

@Preview
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        placeholder = "Input your password",
        isPassword = true,
    )
}

@Preview
@Composable
fun CustomTextFieldNumberPreview() {
    CustomTextField(
        placeholder = "Input your phone number",
        isPassword = false,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextFieldNumber(
    placeholder: String,
    onValueChanged: (String) -> Unit = {},
    errorText: String = ""
) {
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newValue ->
            if (newValue.isDigitsOnly()) {
                text = newValue
                onValueChanged(newValue)
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                fontFamily = FontFamily(fontPoppinsRegular),
            )
        },
        isError = errorText.isNotEmpty(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(fontPoppinsRegular)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    )
    if (errorText.isNotEmpty()) {
        Text(
            text = errorText,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(fontPoppinsRegular)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    placeholder: String,
    isPassword: Boolean = false,
    onValueChanged: (String) -> Unit = {},
    errorText: String = ""
) {
    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(isPassword) }

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            text = it
            onValueChanged(it)
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 16.sp,
                fontFamily = FontFamily(fontPoppinsRegular),
            )
        },
        isError = errorText.isNotEmpty(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = FontFamily(fontPoppinsRegular)
        ),
        visualTransformation = if (passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility }
            ) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Check else Icons.Filled.Close,
                    contentDescription = "Toggle password visibility",
                    tint = if (passwordVisibility) Color.Gray else Color.Black
                )
            }
        }
    )

    if (errorText.isNotEmpty()) {
        Text(
            text = errorText,
            color = Color.Red,
            fontSize = 12.sp,
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(fontPoppinsRegular)
        )
    }
}
