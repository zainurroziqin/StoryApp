package com.rozi.storyapp.di

import android.content.Context
import com.rozi.storyapp.data.StoryRepository
import com.rozi.storyapp.data.lokal.database.StoryDatabase
import com.rozi.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(context,database, apiService)
    }
}