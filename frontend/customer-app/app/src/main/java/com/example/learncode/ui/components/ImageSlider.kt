package com.example.learncode.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ImageItem(imageRes: Int) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .height(150.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSliderWithIndicator(imageList: List<Int>) {
    val pagerState = rememberPagerState(pageCount = {
        imageList.size
    })
    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(3000) // Đợi trong 3 giây
            with(pagerState) {
                val nextPage = if (currentPage < pageCount - 1) currentPage + 1 else 0
                animateScrollToPage(nextPage)
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
        ) { page ->
            ImageItem(imageRes = imageList[page])
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color(0xFF9C7055) else Color(0xFFEDE0CF)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}


