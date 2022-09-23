package com.rieka.herbaldetector.data.api

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("prediksi")
    suspend fun getPrediction(
        @Part file: MultipartBody.Part,
    ): Response<String>
}