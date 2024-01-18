package com.notfoundteam.careergrowapp.data.api

import com.google.gson.JsonObject
import com.notfoundteam.careergrowapp.data.response.LoginResponse
import com.notfoundteam.careergrowapp.data.response.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(
        @Header("Content-Type") contentType: String,
        @Body requestBody: JsonObject
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Header("Content-Type") contentType: String,
        @Body requestBody: JsonObject
    ): Response<LoginResponse>

}