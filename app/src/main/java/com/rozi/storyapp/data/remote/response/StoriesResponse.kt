package com.rozi.storyapp.data.remote.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoriesResponse(
	val listStory: List<ListStoryItem>,
	val error: Boolean? = null,
	val message: String? = null
)

//data class ListStoryItem(
//	val photoUrl: String? = null,
//	val createdAt: String? = null,
//	val name: String? = null,
//	val description: String? = null,
//	val lon: Double? = null,
//	val id: String? = null,
//	val lat: Double? = null
//)

@Entity(tableName = "story")
data class ListStoryItem(
	@PrimaryKey
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

//    @ColumnInfo(defaultValue = "30.8")
	@field:SerializedName("lon")
	val lon: Double? = null,

//    @ColumnInfo(defaultValue = "30.8")
	@field:SerializedName("lat")
	val lat: Double? = null,
)

