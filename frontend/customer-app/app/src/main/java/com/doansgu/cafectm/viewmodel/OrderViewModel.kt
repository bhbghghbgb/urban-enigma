package com.doansgu.cafectm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.Order
import com.doansgu.cafectm.repository.OrderRepository
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
    val orders: LiveData<List<Order>> = _orders

    private val _ordersDelivered = MutableLiveData<List<Order>>()
    val orderDelivered: LiveData<List<Order>> = _ordersDelivered

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _order = MutableLiveData<Order>()
    val order: LiveData<Order> = _order

    fun getListNameProducts(orderId: String, status: Boolean): String {
        var name = ""
        if (status) {
            _ordersDelivered.value?.let { orders ->
                val orderFound = orders.find { it.id == orderId }
                orderFound?.let { order ->
                    order.detailOrders.forEachIndexed { index, item ->
                        if (index != 0) {
                            name += ", "
                        }
                        name += item.product.name
                    }
                }
            }
        } else {
            _orders.value?.let { orders ->
                val orderFound = orders.find { it.id == orderId }
                orderFound?.let { order ->
                    order.detailOrders.forEachIndexed { index, item ->
                        if (index != 0) {
                            name += ", "
                        }
                        name += item.product.name
                    }
                }
            }
        }
        return name
    }

    fun getTotalOrderNotDiscount(orderId: String): Double {
        var total = 0.0
        _order.value?.let { order ->
            if (order.id == orderId) {
                order.detailOrders.forEach { item ->
                    val product = item.product
                    if (product != null) {
                        total += product.price?.times(item.amount) ?: 0.0
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
                        total += product.price?.times(item.amount) ?: 0.0
                    }
                }
                discount = order.discount
            }
        }

        return total - discount
    }

    fun getOrdersNotYetDelivered() {
        _state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getOrdersNotYetDelivered()
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

    fun getOrdersDelivered() {
        _state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getOrdersDelivered()
                if (response.isSuccessful) {
                    _ordersDelivered.postValue(response.body())
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
                Log.d("ERROR", e.message.toString())
            }
        }
    }
}