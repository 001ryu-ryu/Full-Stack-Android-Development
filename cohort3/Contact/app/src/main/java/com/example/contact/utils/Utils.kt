package com.example.contact.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.IOException

object Utils {
    fun compressImage(imageBytes: ByteArray, quality: Int = 70): ByteArray? {
        return try {
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(
                Bitmap.CompressFormat.JPEG, quality, outputStream
            )
            outputStream.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}