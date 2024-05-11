package com.doansgu.cafectm.model

import com.doansgu.cafectm.R

sealed class OnboardingSheet(
    val image: Int,
    val title: String,
    val des: String
) {
    object Page1 : OnboardingSheet(
        image = R.drawable.bg_onboarding_1,
        title = "Welcome to\nCoffee Shop",
        des = "Discover the joy of fresh coffee at your fingertips with Coffee Shop. Explore our premium coffee collection and experience the convenience of ordering from the comfort of your home!"
    )

    object Page2 : OnboardingSheet(
        image = R.drawable.bg_onboarding_2,
        title = "Explore Our Coffee Collection",
        des = "From bold espresso blends to aromatic single-origin coffees, Coffee Shop offers a range of coffees to suit every palate. Explore our curated collection and find the perfect brew for you."
    )

    object Page3 : OnboardingSheet(
        image = R.drawable.bg_onboarding_3,
        title = "Start Your Coffee Journey Today!",
        des = "Don't wait to experience the best coffee experience. Get started with Coffee Shop now and enhance your mornings with perfect coffee delivered straight to your door!"
    )
}