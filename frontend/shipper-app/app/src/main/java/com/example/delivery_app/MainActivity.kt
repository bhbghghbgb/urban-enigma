package com.example.delivery_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.delivery_app.repository.HelloRepository
import com.example.delivery_app.ui.screen.QRCodeScreen
import com.example.delivery_app.ui.screen.QRTestScreen
import com.example.delivery_app.ui.theme.DeliveryappTheme
import com.example.delivery_app.util.GmsBarcodeInit
import com.example.delivery_app.viewmodel.QRTestViewModel
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val qrCodeViewModel: QRTestViewModel by viewModels()
    private val gms = GmsBarcodeInit()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("HelloBackend", "onCreate: ${HelloRepository.helloBackend()}")
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
//                    MainNavHost()
                    QRTestScreen(qrCodeViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeliveryappTheme {
        QRCodeScreen()
    }
}