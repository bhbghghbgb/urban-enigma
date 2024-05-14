package com.example.delivery_app.util

import android.app.Activity
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning

class GmsBarcodeInit {
    private var installed = false
    private var failed = false
    private val options =
        GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .enableAutoZoom() // available on 16.1.0 and higher
            .allowManualInput().build()

    private fun requestInstallModules(
        activity: Activity, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}
    ) {
        ModuleInstall.getClient(activity).installModules(
            ModuleInstallRequest.newBuilder().addApi(GmsBarcodeScanning.getClient(activity)).build()
        ).addOnSuccessListener {
            if (it.areModulesAlreadyInstalled()) {
                installed = true
                onSuccess()
            }
        }.addOnFailureListener { e ->
            failed = true
            onFailure(e)
        }
    }

    fun tryOpenScanner(
        activity: Activity,
        onSuccess: (Barcode) -> Unit = {},
        onCancelled: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        if (failed) {
            onFailure(Exception("Module install failed"))
            return
        }
        if (!installed) {
            requestInstallModules(
                activity, { openScanner(activity, onSuccess, onCancelled, onFailure) }, onFailure
            )
            onFailure(Exception("Module installation requested"))
            return
        }
        openScanner(activity, onSuccess, onCancelled, onFailure)
    }

    private fun openScanner(
        activity: Activity,
        onSuccess: (Barcode) -> Unit = {},
        onCancelled: () -> Unit = {},
        onFailure: (Exception) -> Unit = {}
    ) {
        GmsBarcodeScanning.getClient(activity, options).startScan().addOnSuccessListener(onSuccess)
            .addOnCanceledListener(onCancelled).addOnFailureListener(onFailure)
    }
}