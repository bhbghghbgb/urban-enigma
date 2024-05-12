package com.doansgu.cafectm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.Product2
import com.doansgu.cafectm.model.RetrofitInstance
import kotlinx.coroutines.launch

class HomeScreen2ViewModel : ViewModel() {
    //    private val _threeProductList = MutableStateFlow(HomeScreen2())
//    val threeProductList = _threeProductList.asStateFlow()
    private var _productListLiveData = MutableLiveData<List<Product2>>()
    val productList: LiveData<List<Product2>> get() = _productListLiveData
    private val _stateCallFullPopular = MutableLiveData<Boolean>(false)
    val stateCallFullPopular: LiveData<Boolean> get() = _stateCallFullPopular

    // TODO: use api instead of json file
    init {
        fetchData()
//        viewModelScope.launch {
//            _threeProductList.value = Json.decodeFromString(HomeScreenRepository.jsonTestData())
//        }
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
//                val response = RetrofitInstance.apiService.getProductListLimit()
                val response = RetrofitInstance.apiService.getAllProducts()
                if (response.isSuccessful) {
                    response.body()?.let { _productListLiveData.postValue(it) }
                } else {
                    Log.d("Failed", "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
            }
        }
    }

    fun fetchFullData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiService.getProductList()
                if (response.isSuccessful) {
                    val data = response.body()
                    _productListLiveData.postValue(data!!)
                    _stateCallFullPopular.postValue(true)
                } else {
                    Log.d("Failed", "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
            }
        }
    }
}