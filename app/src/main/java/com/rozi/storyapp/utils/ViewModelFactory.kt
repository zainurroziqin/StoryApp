package com.rozi.storyapp.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rozi.storyapp.di.Injection
import com.rozi.storyapp.ui.addstory.AddstoryViewModel
import com.rozi.storyapp.ui.detailstory.DetailstoryViewModel
import com.rozi.storyapp.ui.login.LoginViewModel
import com.rozi.storyapp.ui.main.MainViewModel
import com.rozi.storyapp.ui.maps.MapsViewModel

class ViewModelFactory private constructor(private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(Injection.provideRepository(mApplication)) as T
        }else if(modelClass.isAssignableFrom(AddstoryViewModel::class.java)){
            return AddstoryViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(DetailstoryViewModel::class.java)){
            return DetailstoryViewModel(mApplication) as T
        }else if(modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: ${modelClass.name}")
    }
}