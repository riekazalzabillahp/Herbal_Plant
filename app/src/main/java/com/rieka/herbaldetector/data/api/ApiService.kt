package com.rieka.herbaldetector.data.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("prediksi")
    fun getPrediction(
        @Part file: MultipartBody.Part,
    ): Call<String>
}