package com.example.flow

import com.example.flow.Data.CheckList
import com.example.flow.Data.LoginData
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {

    @Headers("Content-Type: application/json")
    @POST("api/login")
    suspend fun login(
        @Body body: LoginData
    ): List<LoginData>

    @Headers("Content-Type: application/json")
    @POST("api/subscribe")
    suspend fun lunchList(
        @Body body: CheckList
    ): List<CheckList>

}