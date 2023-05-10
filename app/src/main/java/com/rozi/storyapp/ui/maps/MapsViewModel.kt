package com.rozi.storyapp.ui.maps

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.ListMapsItem
import com.rozi.storyapp.data.remote.response.ListStoryItem
import com.rozi.storyapp.data.remote.response.MapsResponse
import com.rozi.storyapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(application: Application) : ViewModel(){

    private val _listMapsStory = MutableLiveData<List<ListMapsItem>>()
    val listMapstory: LiveData<List<ListMapsItem>> = _listMapsStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val preferences = TokenPreferences(application)

    init {
        getMapsStory()
    }

    private fun getMapsStory() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMapsStory("Bearer ${preferences.getToken()}")
        client.enqueue(object : Callback<MapsResponse>{
            override fun onResponse(call: Call<MapsResponse>, response: Response<MapsResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listMapsStory.value = response.body()?.listStory!!
                }
            }
            override fun onFailure(call: Call<MapsResponse>, t: Throwable) {
            }

        })
    }
}