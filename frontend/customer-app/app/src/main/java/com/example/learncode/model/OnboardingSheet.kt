package com.example.learncode.model

import android.media.Image
import com.example.learncode.R

sealed class OnboardingSheet(
    val image: Int,
    val title: String,
    val des: String
) {
    object Page1 : OnboardingSheet (
        image = R.drawable.affogato,
        title = "Page1",
        des = ""
    )
    object Page2 : OnboardingSheet (
        image = R.drawable.affogato,
        title = "Page2",
        des = ""
    )
    object Page3 : OnboardingSheet (
        image = R.drawable.affogato,
        title = "Page3",
        des = ""
    )
}