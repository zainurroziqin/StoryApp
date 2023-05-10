package com.rozi.storyapp.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.remote.response.LoginResponse
import com.rozi.storyapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _successLogin = MutableLiveData<Boolean>()
    val successLogin : LiveData<Boolean> = _successLogin

    private val preferences = TokenPreferences(application)

    fun login(email : String, password: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object  : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                _successLogin.value = response.isSuccessful
                preferences.setToken(response.body()?.loginResult?.token ?: "")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _successLogin.value = false
            }
        })
    }
}