package com.rozi.storyapp.utils

import androidx.paging.PagingData
import com.rozi.storyapp.data.lokal.database.StoryEntity

object DataDummy {
    fun generateDummyStoryEntity() : List<StoryEntity> {
        val storyList = ArrayList<StoryEntity>()
        for (i in 0..10){
            val story = StoryEntity(
                "id $i",
                "Story ke-$i",
                "Descripsi story ke-$i",
                "2023-05-13T14:37:17.099Z",
                "https://story-api.dicoding.dev/images/stories/photos-1683988637098_FvK66urQ.jpg",
                5.537737,
                95.33695
            )
            storyList.add(story)
        }
        return storyList
    }
}