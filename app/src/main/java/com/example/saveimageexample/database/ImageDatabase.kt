package com.example.saveimageexample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.saveimageexample.database.entity.ImageEntity


@Database(
    entities = [
        ImageEntity::class,
    ],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ImageDatabase : RoomDatabase() {
    abstract fun getDao(): ImageDao
}