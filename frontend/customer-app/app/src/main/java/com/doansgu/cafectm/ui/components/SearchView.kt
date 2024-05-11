package com.doansgu.cafectm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    navController: NavController,
    isNavigated: Boolean,
    onclick: () -> Unit,
    onSearch: (String) -> Unit,
    focusRequester: FocusRequester? = null
) {
    var text by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(Unit) {
        focusRequester?.requestFocus()
    }
    Box(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Card(
            modifier = Modifier.wrapContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(48.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(Color.White)
            ) {
                TextField(
                    value = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester ?: FocusRequester())
                        .focusable()
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused && !isNavigated) {
                                navController.navigate("menuproduct") {
                                    launchSingleTop = true
                                }
                            }
                        },
                    onValueChange = {
                        text = it
                        onSearch(it)
                    },
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
                    trailingIcon = {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(50.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                            modifier = Modifier.padding(5.dp)
                        ) {
                            IconButton(
                                onClick = onclick,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "SearchIcon",
                                    tint = Color(0xFF9d7055),
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
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
}