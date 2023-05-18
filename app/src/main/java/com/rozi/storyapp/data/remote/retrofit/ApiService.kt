package com.rozi.storyapp.data.remote.retrofit

import com.rozi.storyapp.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<LoginResponse>

    @GET("stories")
    suspend fun getListStory(
        @Header("Authorization") token : String,
        @Query("page") page : Int,
        @Query("size") size : Int
    ) : StoriesResponse

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat : RequestBody?,
        @Part("lon") lon : RequestBody?
    ) : Call<AddstoryResponse>

    @GET("stories/{title}")
    fun getDetailStory(
        @Header("Authorization") token : String,
        @Path(value = "title", encoded = true) title : String
    ) : Call<DetailStoryResponse>

    @GET("stories?location=1")
    fun getMapsStory(
        @Header("Authorization") token : String
    ) : Call<MapsResponse>
}