package com.example.learncode.repository

import com.example.learncode.App
import com.example.learncode.R

class HomeScreenRepository {
    companion object {
        fun jsonTestData(): String {
            App.resources.openRawResource(R.raw.homescreen_testdata).bufferedReader()
                .use { return it.readText() }
        }
    }
}