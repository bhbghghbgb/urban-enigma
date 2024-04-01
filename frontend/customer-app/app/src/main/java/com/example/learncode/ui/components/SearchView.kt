package com.example.learncode.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(navController: NavController, modifier: Modifier, isNavigated: Boolean) {
    var text by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Card(
            modifier = modifier.wrapContentSize(),
            shape = RoundedCornerShape(48.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            TextField(
                value = text,
                modifier = modifier.fillMaxWidth().focusable().onFocusChanged { focusState ->
                    if (focusState.isFocused && !isNavigated) {
                        navController.navigate("menuproduct") {
                            launchSingleTop = true
                        }
                    }
                },
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "Search Products...",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontFamily = FontFamily(fontPoppinsSemi)
                    )
                },
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(fontPoppinsRegular)
                ),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "SearchIcon")
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}