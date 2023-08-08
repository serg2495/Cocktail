package com.example.cocktailbar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import java.io.ByteArrayOutputStream

object Utils {

    fun imgToString(ivPhoto: ImageView): String {
        ivPhoto.isDrawingCacheEnabled = true
        val bitmap = ivPhoto.drawingCache
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
    }

    fun stringToImg(data: String): Bitmap {
        val decode = Base64.decode(data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }
}