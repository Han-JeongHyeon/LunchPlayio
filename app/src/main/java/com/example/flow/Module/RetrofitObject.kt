package com.example.flow.Module

import android.app.Application
import android.content.Context
import android.media.audiofx.DynamicsProcessing
import android.util.Log
import com.example.flow.Api
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.CookieManager


class AddCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val response = chain.proceed(chain.request())
//        requestBuilder.addHeader("cookie","token=14")
        requestBuilder.addHeader("cookie","${response.headers("set-cookie")}")

//        Log.d("TAGa", "${response.headers("set-cookie")}")
        response.close()

        return chain.proceed(requestBuilder.build())
    }

}

class RetrofitObject(private var context: Context) {

    private fun getRetrofit(): Retrofit{

        val interceptor = AddCookieInterceptor()

        val interceptorBody = HttpLoggingInterceptor()
        interceptorBody.setLevel(HttpLoggingInterceptor.Level.BODY)
//
        Log.d("taga", "${interceptor}")

        val cache = Cache(File(context.cacheDir,"HTTP_Cache"),10 * 1024 * 1024L)

        val client = OkHttpClient.Builder()
            .cookieJar(JavaNetCookieJar(CookieManager()))
//            .addNetworkInterceptor(interceptor)
            .addInterceptor(interceptorBody)
            .addInterceptor(interceptor)
            .cache(cache)
            .build()

//        Log.d("TAG","${client.cookieJar}")

        return Retrofit.Builder()
            .baseUrl("https://lunch.playio.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getRetrofitService(): Api {
        return getRetrofit().create(Api::class.java)
    }

}