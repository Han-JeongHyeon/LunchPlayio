package com.example.flow.Module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.media.audiofx.DynamicsProcessing
import android.util.Log
import com.example.flow.Api
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.net.CookieManager


class AddCookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val response = chain.proceed(chain.request())

        if (chain.request().url.toString() == "https://lunch.playio.kr/api/login" && response.code == 200) {
            App.prefs.token = response.headers("set-cookie")[0]
        }

        requestBuilder.addHeader("cookie","${App.prefs.token}")

        response.close()

        return chain.proceed(requestBuilder.build())
    }

}

class RetrofitObject(private var context: Context) {

    private fun getRetrofit(): Retrofit{

        val interceptor = AddCookieInterceptor()

        val interceptorBody = HttpLoggingInterceptor()
        interceptorBody.setLevel(HttpLoggingInterceptor.Level.BODY)

        val cache = Cache(File(context.cacheDir,"HTTP_Cache"),10 * 1024 * 1024L)

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
            .addInterceptor(interceptorBody)
            .addInterceptor(interceptor)
            .cache(cache)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://lunch.playio.kr/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getRetrofitService(): Api {
        return getRetrofit().create(Api::class.java)
    }

}