package com.doansgu.cafectm.repository

import com.doansgu.cafectm.App
import com.doansgu.cafectm.R

class HomeScreenRepository {
    companion object {
        fun jsonTestData(): String {
            App.resources.openRawResource(R.raw.homescreen_testdata).bufferedReader()
                .use { return it.readText() }
        }
    }
}