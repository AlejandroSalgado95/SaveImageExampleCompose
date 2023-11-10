package com.example.saveimageexample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.saveimageexample.database.entity.ImageEntity

@Dao
interface ImageDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun setImage(imageItem: ImageEntity)

    @Query("SELECT * FROM image_table")
    suspend fun getImage() :  ImageEntity

}