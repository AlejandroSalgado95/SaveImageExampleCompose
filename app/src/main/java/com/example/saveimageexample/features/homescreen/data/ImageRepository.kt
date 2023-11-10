package com.example.saveimageexample.features.homescreen.data

import com.example.saveimageexample.database.ImageDao
import com.example.saveimageexample.database.entity.ImageEntity
import com.example.saveimageexample.utils.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ImageRepository @Inject constructor(
    private val localDataSource: ImageDao,
) : BaseRepository() {

    suspend fun getImage(): ImageEntity {
        return withContext(Dispatchers.IO) {
            executeLocalResponse(localDataSource.getImage())
        }
    }

    suspend fun setImage(imageEntity: ImageEntity) {
        return withContext(Dispatchers.IO) {
            executeLocalResponse(localDataSource.setImage(imageEntity))
        }
    }
}