package com.example.learncode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.HomeScreen2
import com.example.learncode.repository.HomeScreenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class HomeScreen2ViewModel : ViewModel() {
    private val _threeProductList = MutableStateFlow(HomeScreen2())
    val threeProductList = _threeProductList.asStateFlow()

    // TODO: use api instead of json file
    init {
        viewModelScope.launch {
            _threeProductList.value = Json.decodeFromString(HomeScreenRepository.jsonTestData())
        }
    }
}