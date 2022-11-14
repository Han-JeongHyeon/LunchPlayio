package com.example.flow.Module

import android.app.Application
import android.content.Context
import android.media.audiofx.DynamicsProcessing
import android.util.Log
import com.example.flow.Api
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RetrofitObject(private var context: Context) {

    private fun getRetrofit(): Retrofit{

        val cache = Cache(File(context.cacheDir,"HTTP_Cache"),10 * 1024 * 1024L)

        val client = OkHttpClient.Builder()
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://lunch.playio.kr/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetrofitService(): Api {
        return getRetrofit().create(Api::class.java)
    }

}