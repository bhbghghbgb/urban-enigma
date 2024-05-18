package com.doansgu.cafectm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.Product2
import com.doansgu.cafectm.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreen2ViewModel : ViewModel() {
    //    private val _threeProductList = MutableStateFlow(HomeScreen2Products())
//    val threeProductList = _threeProductList.asStateFlow()
    private var _productListForYouLiveData = MutableLiveData<List<Product2>>()
    val productListForYou: LiveData<List<Product2>> get() = _productListForYouLiveData

    private val _productListBestSellers = MutableStateFlow<List<Product2>>(emptyList())
    val productListBestSellers = _productListBestSellers.asStateFlow()

    private val _stateCallFullPopular = MutableLiveData<Boolean>(false)
    val stateCallFullPopular: LiveData<Boolean> get() = _stateCallFullPopular

    // TODO: use api instead of json file
    init {
        fetchData()
//        viewModelScope.launch {
//            _threeProductList.value = Json.decodeFromString(HomeScreenRepository.jsonTestData())
//        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
//                val response = RetrofitInstance.apiService.getProductListLimit()
                val homeScreen2Products = ProductRepository.getHomeScreen2Products()
                homeScreen2Products?.let {
                    _productListForYouLiveData.postValue(
                        it.forYou ?: emptyList()
                    )
                    _productListBestSellers.value = it.bestSellers ?: emptyList()
                }
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
            }
        }
    }
}