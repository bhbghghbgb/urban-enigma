package com.example.qrcodescanner.ViewModel

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class QrCodeScannerViewModel : ViewModel() {
    val _qrCodeScannerActivityEvent = Channel<String>()
    val qrCodeScannerActivityEvent = _qrCodeScannerActivityEvent.receiveAsFlow()

    private val _textResult = MutableStateFlow("")
    val textResult:StateFlow<String> = _textResult
    init {
        viewModelScope.launch {
            qrCodeScannerActivityEvent.collect {
            onQrScanned(it)
            }
        }
    }
    fun onQrScanned(text: String) {
        // TODO: handle
    }
    fun onQrScanRequest() {
        _qrCodeScannerActivityEvent.send
    }
}
