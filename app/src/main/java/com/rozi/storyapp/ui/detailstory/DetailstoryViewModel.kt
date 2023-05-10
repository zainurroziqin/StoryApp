package com.rozi.storyapp.ui.detailstory

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.DetailStoryResponse
import com.rozi.storyapp.data.remote.response.Story
import com.rozi.storyapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailstoryViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _data = MutableLiveData<Story>()
    val data: LiveData<Story> = _data

    private val tokenPreferences = TokenPreferences(application)

    private val token = tokenPreferences.getToken()

    fun getDetailStory(title: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailStory("Bearer $token", title)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _data.value = response.body()?.story!!
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }
}