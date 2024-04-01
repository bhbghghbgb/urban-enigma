package com.example.learncode.ui.components

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learncode.R
import com.example.learncode.ui.theme.fontPoppinsSemi

@Composable
fun FilledButtonExample(onClick: () -> Unit, text: String, backgroundColor: ButtonColors) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 5.dp),
            fontSize = 20.sp,
            fontFamily = FontFamily(fontPoppinsSemi)
        )
    }
}

@Composable
fun IconButtonCustom(
    onClick: () -> Unit,
    text: String,
    backgroundColor: ButtonColors,
    border: BorderStroke,
    icon: Int,
    fontcolor: Color
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = backgroundColor,
        border = border
    ) {
        Image(
            painter = painterResource(id = icon), contentDescription = "",
            alignment = Alignment.CenterStart
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = text,
                modifier = Modifier.padding(vertical = 5.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily(fontPoppinsSemi),
                textAlign = TextAlign.Center,
                color = fontcolor
            )
        }
    }
}

@Composable
fun NewsButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text
        )
    }
}

@Composable
fun NewTextButton(
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(text)
    }
}