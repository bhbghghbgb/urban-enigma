package com.doansgu.cafectm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.Product2
import com.doansgu.cafectm.model.Products
import com.doansgu.cafectm.model.RetrofitInstance
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val apiService = RetrofitInstance.apiService
    private var _productListLiveData = MutableLiveData<List<Products>>()

    val productList: LiveData<List<Products>> get() = _productListLiveData

    init {
        val name = ""
        fetchData(name)
    }

    fun resetProducts() {
        _productListLiveData.postValue(emptyList())
    }

    fun fetchData(name: String) {
        viewModelScope.launch {
            try {
                val response = apiService.searchProductByName(name)
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
}