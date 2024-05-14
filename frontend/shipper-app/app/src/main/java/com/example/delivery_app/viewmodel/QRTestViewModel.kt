package com.example.delivery_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class QRTestViewModel : ViewModel() {
    private val _message = MutableStateFlow("Click button to open GmsBarcodeInit")
    val message = _message.asStateFlow()

    private val _openScanner = Channel<Unit>(Channel.RENDEZVOUS)
    val openScanner = _openScanner.receiveAsFlow()

    fun openScanner() {
        viewModelScope.launch {
            _openScanner.send(Unit)
        }
        Log.d("QRScanner", "Sent an event")
    }

    fun onScanSuccess(barcode: Barcode) {
        _message.value = "ScanSuccess\n${barcode.format}\n{${barcode.rawValue}}"
    }

    fun onScanCancelled() {
        _message.value = "ScanCanceled"
    }

    fun onScanFailure(e: Exception) {
        _message.value = "ScanFailure\n${e.message}"
    }
}