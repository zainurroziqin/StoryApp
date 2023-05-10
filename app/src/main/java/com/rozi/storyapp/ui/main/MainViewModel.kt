package com.rozi.storyapp.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rozi.storyapp.data.StoryRepository
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.ListStoryItem
//import com.rozi.storyapp.data.remote.response.StoriesResponse
import com.rozi.storyapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<ListStoryItem>> = storyRepository.getStory().cachedIn(viewModelScope)

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

//    private val preferences = TokenPreferences(application)

//    init {
//        getListStory()
//    }

//     fun getListStory() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getListStory("Bearer ${preferences.getToken()}")
//        client.enqueue(object : Callback<StoriesResponse> {
//            override fun onResponse(
//                call: Call<StoriesResponse>,
//                response: Response<StoriesResponse>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    if (response.body()?.listStory?.isEmpty() != false) {
//                        _success.value = true
//                        _listStory.value = response.body()?.listStory!!
//                    } else {
//                        _success.value = false
//                        _listStory.value = response.body()?.listStory!!
//                    }
//                } else {
//                    _success.value = false
//                }
//            }
//
//            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "OnFailure: ${t.message}")
//                _success.value = false
//            }
//        })
//    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}