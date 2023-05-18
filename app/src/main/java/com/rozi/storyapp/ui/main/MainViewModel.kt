package com.rozi.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rozi.storyapp.data.StoryRepository
import com.rozi.storyapp.data.lokal.database.StoryEntity

class MainViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<StoryEntity>> = storyRepository.getStory().cachedIn(viewModelScope)

}