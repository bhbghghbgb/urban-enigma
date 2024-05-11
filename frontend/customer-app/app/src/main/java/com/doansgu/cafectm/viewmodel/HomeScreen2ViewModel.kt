package com.doansgu.cafectm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.HomeScreen2
import com.doansgu.cafectm.repository.HomeScreenRepository
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