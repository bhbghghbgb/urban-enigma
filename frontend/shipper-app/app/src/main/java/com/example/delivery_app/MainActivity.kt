package com.example.delivery_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.delivery_app.repository.HelloRepository
import com.example.delivery_app.ui.navigation.MainNavHost
import com.example.delivery_app.ui.screen.QRTestScreen
import com.example.delivery_app.ui.theme.DeliveryappTheme
import com.example.delivery_app.util.GmsBarcodeInit
import com.example.delivery_app.viewmodel.AuthViewModel
import com.example.delivery_app.viewmodel.QRTestViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val qrCodeViewModel: QRTestViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val gms = GmsBarcodeInit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("HelloBackend", "onCreate: ${HelloRepository.helloBackend()}")
                launch {
                    authViewModel.requestSendCode.collect {
                        Log.d("Auth", "Collected an event")
                        authViewModel.sendCode(it, this@MainActivity)
                    }
                }
                launch {
                    qrCodeViewModel.openScanner.collect {
                        Log.d("QRScanner", "Collected an event")
                        gms.tryOpenScanner(this@MainActivity,
                            onSuccess = { barcode -> qrCodeViewModel.onScanSuccess(barcode) },
                            onFailure = { e -> qrCodeViewModel.onScanFailure(e) },
                            onCancelled = { qrCodeViewModel.onScanCancelled() })
                    }
                }
            }
        }
        setContent {
            DeliveryappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainNavHost()
//                    QRTestScreen()
                }
            }
        }
    }
}