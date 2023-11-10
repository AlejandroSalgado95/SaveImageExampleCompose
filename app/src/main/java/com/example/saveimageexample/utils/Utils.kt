package com.example.saveimageexample.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


class Utils {
    companion object {
        fun NumberToTwoDecimalsString(number: Float): String {
            val twoDecimalsFormat = DecimalFormat(".##")
            twoDecimalsFormat.roundingMode = RoundingMode.CEILING
            var stringNumber = twoDecimalsFormat.format(number).toString()
            val integerPlaces = stringNumber.indexOf('.')
            val decimalPlaces = stringNumber.length - integerPlaces - 1
            if (decimalPlaces == 1)
                stringNumber += "0"
            if (number == 0f)
                stringNumber = "0.00"
            return stringNumber
        }

        fun createBlankImageFile(context: Context): Uri? {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val resolver = context.contentResolver

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, timeStamp)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/workmanager_test")
                } else {
                    Environment.getExternalStoragePublicDirectory("${Environment.DIRECTORY_DCIM}/workmanager_test")
                }
            }
            return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        }


        fun reduceImageByteSize(imageBytes: ByteArray, thresholdBytesSize: Int): ByteArray {

            var quality = 100
            var bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            var outputBytes: ByteArray
            do {
                val outputStream = ByteArrayOutputStream()
                outputStream.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    outputBytes = outputStream.toByteArray()
                    quality -= 10
                }
            } while (outputBytes.size > thresholdBytesSize)
            return outputBytes
        }


        suspend fun getBitmapFromUri(uri: Uri?, context: Context): Bitmap? {
            return withContext(Dispatchers.IO) {
                Glide.with(context).asBitmap().load(uri).submit().get()
            }
        }

        fun reduceImageSize(image: Bitmap?): Bitmap? {
            if (image != null) {
                var width = image.width
                var height = image.height
                val maxSize = 500

                val bitmapRatio = width.toFloat() / height.toFloat()
                if (bitmapRatio > 1) {
                    width = maxSize
                    height = (width / bitmapRatio).toInt()
                } else {
                    height = maxSize
                    width = (height * bitmapRatio).toInt()
                }
                return Bitmap.createScaledBitmap(image, width, height, true)
            } else return null
        }
    }
}