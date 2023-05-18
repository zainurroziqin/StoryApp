package com.rozi.storyapp.ui.addstory

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.AddstoryResponse
import com.rozi.storyapp.data.remote.retrofit.ApiConfig
import com.rozi.storyapp.utils.reduceFileImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddstoryViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val mTokenPreferences = TokenPreferences(application)

    fun addStory(filePhoto: File, desc: String, lat: String?, lon : String?) {
        _isLoading.value = true
        val file = reduceFileImage(filePhoto)
        val description = desc.toRequestBody("text/plain".toMediaType())
        val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
        val latitude =lat?.toRequestBody("text/plain".toMediaType())
        val longtitude =lon?.toRequestBody("text/plain".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )
        val apiService = ApiConfig.getApiService()
        val token = "Bearer ${mTokenPreferences.getToken()}"
        val uploadImageRequest = apiService.uploadImage(token,imageMultipart, description, latitude, longtitude)
        uploadImageRequest.enqueue(object : Callback<AddstoryResponse> {
            override fun onResponse(
                call: Call<AddstoryResponse>,
                response: Response<AddstoryResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _success.value = true
                    }
                } else {
                    _isLoading.value = false
                    _success.value = false
                }
            }

            override fun onFailure(call: Call<AddstoryResponse>, t: Throwable) {
                _isLoading.value = false
                _success.value = false
            }
        })
    }
}