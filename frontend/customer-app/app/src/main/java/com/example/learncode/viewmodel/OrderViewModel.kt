package com.example.learncode.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.model.Order
import com.example.learncode.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class State {
    LOADING,
    ERROR,
    SUCCESS
}

class OrderViewModel : ViewModel() {
    private val repository = OrderRepository()

    private val _orders = MutableLiveData<List<Order>>()
    val order: LiveData<List<Order>> = _orders

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun getListNameProducts(orderId: String): String {
        var name: String = ""
        _orders.value?.let { order ->
            val orderFound = order.find { it.id == orderId }
            orderFound?.let {
                it.detailOrders.forEach { item ->
                    name = name + "," + item.product.name
                }
            }
        }
        return name
    }

    fun getOrdersNotYetDelivered(token: String) {
        _state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getOrdersNotYetDelivered(token)
                if (response.isSuccessful) {
                    _orders.postValue(response.body())
                    _state.postValue(State.SUCCESS)
                } else {
                    _state.postValue(State.ERROR)
                }
            } catch (e: Exception) {
                _state.postValue(State.ERROR)
                Log.d("ERROR", e.message.toString())
            }
        }
    }
}