package com.example.learncode.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.learncode.model.OnboardingSheet
import com.example.learncode.ui.components.NewTextButton
import com.example.learncode.ui.components.NewsButton
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavHostController) {
    val pages = listOf(
        OnboardingSheet.Page1, OnboardingSheet.Page2, OnboardingSheet.Page3
    )
    val pageState = rememberPagerState(initialPage = 0) {
        pages.size
    }
    val buttonState = remember {
        derivedStateOf {
            when (pageState.currentPage) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Go")
                else -> listOf("", "")
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(state = pageState) { index ->
            OnboardingSlide(slide = pages[index])
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(start = 10.dp, bottom = 10.dp)
                .align(Alignment.BottomStart)
        ) {
            repeat(pageState.pageCount) { iteration ->
                val color =
                    if (pageState.currentPage == iteration) Color(0xFF9C7055) else Color(0xFFEDE0CF)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 5.dp, end = 10.dp)
        ) {
            val scope = rememberCoroutineScope()
            if (buttonState.value[0].isNotEmpty()) {
                NewTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        scope.launch {
                            pageState.animateScrollToPage(page = pageState.currentPage - 1)
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            NewsButton(text = buttonState.value[1]) {
                scope.launch {
                    if (pageState.currentPage == 2) {
                        navController.navigate("welcome")
                    } else {
                        pageState.animateScrollToPage(page = pageState.currentPage + 1)
                    }
                }
            }
        }
    }

}


@Composable
fun OnboardingSlide(modifier: Modifier = Modifier, slide: OnboardingSheet) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.6f),
            painter = painterResource(id = slide.image),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = slide.title,
            fontSize = 25.sp,
            fontFamily = FontFamily(fontPoppinsSemi),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF9d7055),
            maxLines = 2,
            lineHeight = 30.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = slide.des,
            fontSize = 16.sp,
            fontFamily = FontFamily(fontPoppinsRegular),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFFA0A0A0)
        )
    }
}