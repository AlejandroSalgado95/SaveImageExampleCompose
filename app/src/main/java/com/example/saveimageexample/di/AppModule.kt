package com.example.saveimageexample.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.saveimageexample.R
import com.example.saveimageexample.database.ImageDao
import com.example.saveimageexample.database.ImageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ImageDatabase {
        return Room.databaseBuilder(context, ImageDatabase::class.java, "example_db.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: ImageDatabase): ImageDao {
        return database.getDao()
    }

}

