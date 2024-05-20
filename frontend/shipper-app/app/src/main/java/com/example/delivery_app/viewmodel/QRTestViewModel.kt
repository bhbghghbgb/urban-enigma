package com.example.delivery_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app.model.Customer
import com.example.delivery_app.model.IncreasePoint
import com.example.delivery_app.repository.UserRepository
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class QRTestViewModel : ViewModel() {
    private val repository = UserRepository()
    private val _increaseMembershipPointResult = MutableLiveData<Result<Customer>>()
    val increaseMembershipPointResult: LiveData<Result<Customer>> = _increaseMembershipPointResult

    private val _scanSuccess = MutableStateFlow(true)
    val scanSuccess = _scanSuccess.asStateFlow()

    private val _point = MutableStateFlow(0)

    private val _message = MutableStateFlow("Click button to open GmsBarcodeInit")
    val message = _message.asStateFlow()

    private val _openScanner = Channel<Unit>(Channel.CONFLATED)
    val openScanner = _openScanner.receiveAsFlow()

    fun openScanner() {
        viewModelScope.launch {
            _openScanner.send(Unit)
        }
        Log.d("QRScanner", "Sent an event")
        Log.d("FlowTest", "openScanner: $openScanner")
        Log.d("FlowTest", "openScanner: $this")
    }

    fun onScanSuccess(barcode: Barcode) {
        _message.value = "ScanSuccess\n${barcode.format}\n{${barcode.rawValue}}"
        _scanSuccess.value = true
    }

    fun onScanCancelled() {
        _message.value = "ScanCanceled"
    }

    fun onScanFailure(e: Exception) {
        _message.value = "ScanFailure\n${e.message}"
    }

    fun setPoint(point: Int) {
        _point.value = point
    }

    fun increaseMembershipPoint() {
        viewModelScope.launch {
            try {
                val username =
                    Regex("doansgu/customer/([a-zA-Z0-9]+)").find(message.value)?.groups?.get(1)?.value
                if (username.isNullOrBlank()) {
                    _increaseMembershipPointResult.value =
                        Result.failure(Exception("Username is null or blank"))
                    return@launch
                }
                val increasePoint = IncreasePoint(username, _point.value)
                val response = repository.increaseMembershipPoint(increasePoint)
                if (response.isSuccessful) {
                    val customer = response.body()?.customer
                    if (customer != null) {
                        _increaseMembershipPointResult.value = Result.success(customer)
                    } else {
                        _increaseMembershipPointResult.value =
                            Result.failure(Exception("Customer is null"))
                    }
                } else {
                    _increaseMembershipPointResult.value =
                        Result.failure(Exception("Failed to increase membership point: ${response.message()}"))
                }
            } catch (e: Exception) {
                _increaseMembershipPointResult.value = Result.failure(e)
            }
        }
    }
}