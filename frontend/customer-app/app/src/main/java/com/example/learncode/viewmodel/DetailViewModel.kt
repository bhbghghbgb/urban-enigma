package com.example.learncode.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.Products
import com.example.learncode.model.RetrofitInstance
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val apiService = RetrofitInstance.apiService
    private var _product: MutableLiveData<Products> = MutableLiveData<Products>()
    val product: LiveData<Products> get() = _product

    fun fetchData(id: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getProduct(id)
                if (response.isSuccessful) {
                    val data = response.body()
                    _product.value = data
                } else {
                    Log.d("Failed", "Response not successful: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("Error", "Error fetching data", e)
            }
        }
    }
}