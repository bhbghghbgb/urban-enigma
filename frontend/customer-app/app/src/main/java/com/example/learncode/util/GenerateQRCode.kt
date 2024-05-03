package com.example.learncode.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import java.util.Hashtable

fun generateQRCode(id: String, size: Int, context: Context): Bitmap? {
    try {
        val hints = Hashtable<EncodeHintType, Any>()
        hints[EncodeHintType.MARGIN] = 0 // Set margin to 0
        val bitMatrix = MultiFormatWriter().encode(id, BarcodeFormat.QR_CODE, size, size, hints)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    } catch (e: WriterException) {
        e.printStackTrace()
    }
    return null
}