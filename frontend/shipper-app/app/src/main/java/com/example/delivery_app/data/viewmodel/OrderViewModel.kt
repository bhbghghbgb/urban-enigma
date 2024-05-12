package com.example.delivery_app.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app.data.model.Order
import com.example.delivery_app.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val repository = OrderRepository()

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    fun getOrderById(id: String) {
        _state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getOrderById(id)
                if (response.isSuccessful) {
                    _order.postValue(response.body())
                    _state.postValue(State.SUCCESS)
                } else {
                    _state.postValue(State.ERROR)
                }
            } catch (e: Exception) {
                _state.postValue(State.ERROR)
            }
        }
    }

    fun getTotalOrderNotDiscount(orderId: String): Double {
        var total = 0.0
        _order.value?.let { order ->
            if (order.id == orderId) {
                order.detailOrders.forEach { item ->
                    val product = item.product
                    if (product != null) {
                        total += product.price * item.amount
                    }
                }
            }
        }

        return total
    }

    fun getTotalOrderHaveDiscount(orderId: String): Double {
        var discount = 0.0
        var total = 0.0
        _order.value?.let { order ->
            if (order.id == orderId) {
                order.detailOrders.forEach { item ->
                    val product = item.product
                    if (product != null) {
                        total += product.price * item.amount
                    }
                }
                discount = order.discount
            }
        }

        return total - discount
    }
}