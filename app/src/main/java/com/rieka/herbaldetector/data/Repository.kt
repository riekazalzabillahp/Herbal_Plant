package com.rieka.herbaldetector.data

import com.rieka.herbaldetector.data.api.ApiConfig
import com.rieka.herbaldetector.data.api.ApiConfig.apiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class Repository {

    suspend fun getPrediction(file: File): Response<String> {
        val imgMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "file",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        return apiService.getPrediction(imgMultipart)
    }
}