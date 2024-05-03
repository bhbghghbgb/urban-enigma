package com.example.learncode.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.ApiService
import com.example.learncode.model.Products
import com.example.learncode.model.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {
    private val apiService = RetrofitInstance.apiService
    private var _productListLiveData = MutableLiveData<List<Products>>()
    val productList: LiveData<List<Products>> get() = _productListLiveData

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = apiService.getProductList()
                if (response.isSuccessful) {
                    val data = response.body()
                    _productListLiveData.postValue(data)
                } else {
                    Log.d("Failed", "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
            }
        }
    }
}