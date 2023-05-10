package com.rozi.storyapp.data.remote.response

data class MapsResponse(
	val listStory: List<ListMapsItem>? = null,
	val error: Boolean? = null,
	val message: String? = null
)

data class ListMapsItem(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Double? = null,
	val id: String? = null,
	val lat: Double? = null
)

