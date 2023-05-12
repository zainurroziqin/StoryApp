package com.rozi.storyapp.data.lokal.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStory(vararg: StoryEntity)

    @Query("SELECT * FROM tbl_story")
    fun getAllStory(): PagingSource<Int, StoryEntity>

    @Query("DELETE FROM tbl_story")
    suspend fun deleteAll()
}