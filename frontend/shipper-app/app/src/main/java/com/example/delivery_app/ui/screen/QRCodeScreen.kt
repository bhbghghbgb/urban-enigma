package com.example.delivery_app.ui.screen

//import android.widget.Toast
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Button
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.navigation.NavController
//
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
