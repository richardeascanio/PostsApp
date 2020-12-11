package com.richard.postsapp.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostRetrofit {

    @GET("posts")
    suspend fun get(): List<PostNetworkEntity>

    @Headers("Content-Type: application/json")
    @POST("posts")
    suspend fun add(@Body post: PostNetworkEntity): PostNetworkEntity
}