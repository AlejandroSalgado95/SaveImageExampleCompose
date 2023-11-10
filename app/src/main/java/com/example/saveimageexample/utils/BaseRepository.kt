package com.example.saveimageexample.utils

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


open class BaseRepository() {
    protected suspend fun <T : Any> executeLocalResponse(response: T): T {
        return response
    }
}