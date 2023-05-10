package com.rozi.storyapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.rozi.storyapp.data.lokal.TokenPreferences
import com.rozi.storyapp.data.lokal.database.StoryDatabase
import com.rozi.storyapp.data.remote.response.ListStoryItem
import com.rozi.storyapp.data.remote.retrofit.ApiService

class StoryRepository( context: Context, private val storyDatabase: StoryDatabase, private val apiService: ApiService) {
    val token = TokenPreferences(context).getToken()
    fun getStory() : LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = StoryRemoteMediator("Bearer ${token}", storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}