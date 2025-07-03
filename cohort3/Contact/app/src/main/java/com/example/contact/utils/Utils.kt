package com.example.contact.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.IOException

object Utils {
    fun compressImage(imageBytes: ByteArray?, quality: Int = 70): ByteArray? {
        return try {
            if (imageBytes == null || imageBytes.isEmpty()) return "".toByteArray()
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size) ?: return null
            val outputStream = ByteArrayOutputStream()
            val success = bitmap.compress(
                Bitmap.CompressFormat.JPEG, quality, outputStream
            )
            if (success) outputStream.toByteArray() else "".toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            "".toByteArray()
        }
    }
}