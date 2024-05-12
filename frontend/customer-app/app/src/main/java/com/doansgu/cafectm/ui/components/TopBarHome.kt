package com.doansgu.cafectm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.doansgu.cafectm.R
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi

@Composable
fun TopBarHome() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF9C7055))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 10.dp, end = 10.dp)
                .background(Color(0xFF9C7055)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(width = 40.dp, height = 40.dp),
                    painter = painterResource(id = R.drawable.iconcoffee),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "NewLands",
                    fontSize = 20.sp,
                    fontWeight = FontWeight(1000),
                    fontFamily = FontFamily(fontPoppinsSemi),
                    color = Color.White
                )
            }
            Icon(
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.iconbell),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}