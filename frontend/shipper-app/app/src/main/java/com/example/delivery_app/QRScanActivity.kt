package com.example.delivery_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class QRScanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Ở đây, bạn có thể xử lý kết quả quét mã QR
                val scannedText = result.contents
                // Ví dụ: Hiển thị kết quả quét mã QR bằng Toast

                Log.d("QR", scannedText)
                Toast.makeText(this, "Scanned QR code: $scannedText", Toast.LENGTH_SHORT).show()
            } else {
                // Xử lý khi người dùng không quét được mã QR hoặc đã hủy quét
            }
            finish()
        }
    }
}