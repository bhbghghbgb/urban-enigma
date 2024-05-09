package com.example.delivery_app.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app.data.model.Order
import com.example.delivery_app.data.repository.AuthRepository
import com.example.delivery_app.data.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = DeliveryRepository()

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _state = MutableLiveData<State>(State.LOADING)
    val state: LiveData<State> = _state
    fun getOrderByStaff(token: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.postValue(State.LOADING)
            try {
                val response = repository.getOrderByStaff(token)
                if (response.isSuccessful) {
                    _state.postValue(State.SUCCESS);
                    _order.postValue(response.body())
                } else {
                    _state.postValue(State.ERROR);
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
                _state.postValue(State.ERROR);
            }
        }
    }

    fun getOrdersByStaff(token: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.postValue(State.LOADING)
            try {
                val response = repository.getOrdersByStaff(token)
                if (response.isSuccessful) {
                    _state.postValue(State.SUCCESS);
                    _orders.postValue(response.body())
                } else {
                    Log.d("Data", response.body().toString())
                    _state.postValue(State.ERROR);
                }
            } catch (e: Exception) {
                Log.d("DataA", e.toString())
                _state.postValue(State.ERROR);
            }
        }
    }

    fun getTotalOrderHaveDiscount(orderId: String): Double {
        var discount = 0.0
        var total = 0.0
        _orders.value?.let { orders ->
            orders.forEach{ order ->
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
        }

        return total - discount
    }

    fun getTotalItem(orderId: String): Int {
        var total = 0
        _orders.value?.let { orders ->
            orders.forEach{ order ->
                if (order.id == orderId) {
                    order.detailOrders.forEach { item ->
                        val product = item.product
                        if (product != null) {
                            total += item.amount
                        }
                    }
                }
            }
        }

        return total
    }
}