package com.example.saveimageexample.database.entity

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "image_table"
)
data class ImageEntity(
    @PrimaryKey
    var id: Int = 0,
    var image: Bitmap? = null
)

