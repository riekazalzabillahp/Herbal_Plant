package com.rieka.herbaldetector.data

import com.rieka.herbaldetector.data.api.ApiConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Repository {
    private val apiClient =  ApiConfig.getApiService()

    fun getPrediction(file: File) : String {
        var result = ""
        val requestImg = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imgMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImg
        )

        apiClient.getPrediction(imgMultipart).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {
                    result = response.body().toString()
                } else {
                    val errorMsg = JSONObject(response.errorBody()?.string()!!)
                    result = errorMsg.getString("message")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                result = t.message.toString()
            }
        })

        return result
    }
}