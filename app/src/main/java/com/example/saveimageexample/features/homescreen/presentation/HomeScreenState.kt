package com.example.saveimageexample.features.homescreen.presentation

import android.media.Image
import com.example.saveimageexample.database.entity.ImageEntity

data class HomeScreenState(
    var imageEntity: ImageEntity = ImageEntity()
)