package com.example.delivery_app.ui.screen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.delivery_app.QRScanActivity
import com.example.delivery_app.R

@Composable
fun QRCodeScreen() {
    val context = LocalContext.current

    // Khai báo launcher để yêu cầu quyền CAMERA
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Nếu quyền được cấp, mở màn hình quét QR
            openQRScanner(context)
        } else {
            Toast.makeText(
                context,
                "Camera permission denied. Cannot open QR scanner.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(onClick = {
                    // Kiểm tra xem quyền camera đã được cấp chưa
                    if (ContextCompat.checkSelfPermission(
                            context,
                            "android.permission.CAMERA"
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        openQRScanner(context)
                    } else {
                        // Nếu chưa được cấp, yêu cầu quyền
                        cameraPermissionLauncher.launch("android.permission.CAMERA")
                    }
                }, backgroundColor = Color.White) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_qrcode_24),
                        contentDescription = null
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .background(Color.Blue)
        ) {
            // Hiển thị hình ảnh của mã QR hoặc bất kỳ hình ảnh nào bạn muốn
            Image(
                painter = painterResource(id = R.drawable.qr_code_scan),
                contentDescription = null
            )
        }
    }
}

private fun openQRScanner(context: Context) {
    val intent = Intent(context, QRScanActivity::class.java)
    context.startActivity(intent)
}


//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun QRScannerScreen(navController: NavController) {
//    val context = LocalContext.current
//    val permissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
//
//    BackHandler(enabled = permissionState.permissionRequested) {
//        permissionState.launchPermissionRequest()
//    }
//
//    when (permissionState.permissionState) {
//        is PermissionState.Allowed -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Button(onClick = { startQRScanner(context) }) {
//                    Text("Scan QR Code")
//                }
//            }
//        }
//        is PermissionState.NotRequested,
//        is PermissionState.ShouldShowRationale -> {
//            // Permission not granted yet, request permission
//            permissionState.launchPermissionRequest()
//        }
//        is PermissionState.Denied -> {
//            // Permission denied by user
//            Toast.makeText(
//                context,
//                "Camera permission denied. Please grant camera permission in settings.",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//}
//
//fun startQRScanner(context: android.content.Context) {
//    val integrator = IntentIntegrator(context as android.app.Activity)
//    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//    integrator.setPrompt("Scan a QR Code")
//    integrator.setCameraId(0)  // Use a specific camera of the device (0 = back camera, 1 = front camera)
//    integrator.setBeepEnabled(true)
//    integrator.setOrientationLocked(true)
//    integrator.initiateScan()
//}
//
//@Preview
//@Composable
//fun PreviewQRScannerScreen() {
//    QRScannerScreen()
//}
