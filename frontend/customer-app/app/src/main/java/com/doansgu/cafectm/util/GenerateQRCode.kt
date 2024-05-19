package com.doansgu.cafectm.util

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.doansgu.cafectm.App
import com.doansgu.cafectm.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import qrcode.QRCode

@SuppressLint("ResourceType")
suspend fun generateQRCode(data: String): ImageBitmap = withContext(Dispatchers.Default) {
    val byteArray = QRCode.ofSquares()
//        .withColor(0xFF9C7055.toInt())
        .withSize(20)
//        .withLogo(App.resources.openRawResource(R.drawable.icongift).readBytes(), 64, 64, false)
        .build(data).renderToBytes()
    return@withContext BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
}