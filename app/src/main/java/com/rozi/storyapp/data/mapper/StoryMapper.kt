package com.rozi.storyapp.data.mapper

import com.rozi.storyapp.data.lokal.database.StoryEntity
import com.rozi.storyapp.data.remote.response.ListStoryItem

fun storyToStoryEntity(story: ListStoryItem): StoryEntity {
    return StoryEntity(
        id = story.id,
        name = story.name,
        description = story.description,
        createdAt = story.createdAt,
        photoUrl = story.photoUrl,
        lat = story.lat,
        lon = story.lon
    )
}